package com.store.itemserver.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.store.commen.utils.ResponseResult;
import com.store.itemserver.mapper.ItemMapper;
import com.store.itemserver.model.dto.ItemDoc;
import com.store.itemserver.model.dto.ItemResult;
import com.store.itemserver.model.po.Item;
import com.store.itemserver.service.IItemService;
import io.jsonwebtoken.lang.Strings;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author 虎哥
 */
@Service

@Slf4j
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements IItemService {
    @Autowired
    private RestHighLevelClient client;


    @Override
    public ResponseResult Page(int size, int page, String key)  {
//        try {
//            PageHelper.startPage((page-1)*size,size);
//            LambdaQueryWrapper<Item> wrapper = new LambdaQueryWrapper<>();
//            wrapper.like(Item::getName,item)
//                    .or()
//                    .like(Item::getCategory,item)
//                    .or()
//                    .like(Item::getBrand,item)
//                    .or()
//                    .like(Item::getSpec,item);
//            List<Item> list = itemMapper.selectList(wrapper);
//            //封装分页结果
//            PageInfo<Item> pageInfo = new PageInfo<>(list);
//            return new ResponseResult(200,"Success",pageInfo);
//        } catch (Exception e) {
//            throw new RuntimeException("查询分页失败");
//        }
        try {
            SearchRequest request = new SearchRequest("item");
            if (!Strings.hasText(key)){
                request.source().query(QueryBuilders.matchAllQuery()).from((page-1)*size).size(size).sort("id", SortOrder.ASC);
            }
            else {
                request.source()
                        .query(QueryBuilders.multiMatchQuery(key,"name","brand","category"))
                        .sort("_score",SortOrder.DESC)
                        .from(page).size(size);
            }
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            return handleResponse(response);
        } catch (IOException e) {
            throw new RuntimeException("ES查询失败!!!");
        }
    }

    private  ResponseResult handleResponse(SearchResponse response) {
        try {
            SearchHits searchHits = response.getHits();
            long total = searchHits.getTotalHits().value;
//        System.out.println("共搜索到" + total + "条数据");
            List<ItemDoc> item_info = new ArrayList<>();
            SearchHit[] hits = searchHits.getHits();
            for (SearchHit hit : hits) {
                String json = hit.getSourceAsString();
                Item item = JSON.parseObject(json, Item.class);
                ItemDoc itemDoc = new ItemDoc(item);
//                log.info(json);
                item_info.add(itemDoc);
//                System.out.println("movieDoc = " + moviesDoc);
            }
            return new ResponseResult(200,"Success",new ItemResult(total,item_info));
        } catch (Exception e) {
            throw new RuntimeException("handleResponse处理失败:" + e);
        }
    }
    @Override
    public ResponseResult suggestion(String prefix) {
        try {
            SearchRequest request = new SearchRequest("item");
            request.source().suggest(new SuggestBuilder().addSuggestion("suggestions"
            , SuggestBuilders.completionSuggestion("suggestion")
                            .prefix(prefix)
                            .skipDuplicates(true)
                            .size(10)
                    ));
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            //解析结果
            ArrayList<String> list_Info = new ArrayList<>();
            List<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> suggestions = response.getSuggest().getSuggestion("suggestions").getEntries();
            for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option> suggestion : suggestions) {
                List<? extends Suggest.Suggestion.Entry.Option> options = suggestion.getOptions();
                for (Suggest.Suggestion.Entry.Option option : options) {
                    String text = option.getText().toString();
                    list_Info.add(text);
                }
            }
//            System.out.println("list_Info:" + list_Info);
            return new ResponseResult(200,"Success",list_Info);
        } catch (IOException e) {
            throw new RuntimeException("ES_suggestion查询失败");
        }
    }
    @Override
    public ResponseResult GetType() {
        try {
            SearchRequest request = new SearchRequest("item");
//        TermsAggregationBuilder brandagg = AggregationBuilders.terms("brandAgg").field("brand");
            request.source().size(0).aggregation(AggregationBuilders.terms("brandAgg").field("brand").size(20));
            request.source().size(0).aggregation(AggregationBuilders.terms("categoryAgg").field("category").size(10));
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            HashMap<String, List<String>> typeInfo = new HashMap<>();
            List<String> brandAgg = getAggResponseResult(response, "brandAgg");
            List<String> categoryAgg = getAggResponseResult(response, "categoryAgg");
            typeInfo.put("brand", brandAgg.subList(0,11));
            typeInfo.put("brand2", brandAgg.subList(11,20));
            typeInfo.put("category",categoryAgg);
            return new ResponseResult<>(200,"Success",typeInfo);

        } catch (IOException e) {
            throw new RuntimeException("查询商品品牌失败");
        }

    }

    @Override
    public Item queryItemByIds(Long id) {
        return getById(id);
    }

    @Override
    public void saveOrUpdateItem(Item item) {
        saveOrUpdate(item);
    }

    //es 处理数据
    @Override
    public void insertById(Long id) {
        try {
//        首先根据ID查询数据库信息
            Item itemInfo = getById(id);
//        将数据转换为文档类型
            ItemDoc itemDoc = new ItemDoc(itemInfo);
//        准备request
            IndexRequest request = new IndexRequest("item").id(itemDoc.getId().toString());
//        准备Json文档
            request.source(JSON.toJSONString(itemDoc), XContentType.JSON);
            client.index(request,RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deteteItemById(Item item) {
        deleteById(item.getId());
    }

    private static List<String> getAggResponseResult(SearchResponse response, String agg) {
        Aggregation aggregation = response.getAggregations().get(agg);
        ArrayList<String> type_temp = new ArrayList<>();
        Terms termsAggregation = aggregation instanceof Terms ? (Terms) aggregation : null;
        if (termsAggregation != null) {
            // 遍历每个桶(bucket)
            for (Terms.Bucket entry : termsAggregation.getBuckets()) {
                String brandName = entry.getKeyAsString(); // 获取brand名称
                type_temp.add(brandName);
            }

        }
        return type_temp;
    }
}
