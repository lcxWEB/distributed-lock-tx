/**
 * Copyright 2020-9999 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.transaction.spring.service;

import io.transaction.spring.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author binghe
 * @version 1.0.0
 * @description 商品Service
 */
@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    @Transactional(propagation = Propagation.NESTED)
    public void updateProductStockCountById(Integer stockCount, Long id){
        productDao.updateProductStockCountById(stockCount, id);

        // int i = 1 / 0;

    }

    // 方法A和方法B
    // Required Required，B抛异常A不捕捉，A、B都回滚；A捕捉B的异常，抛出rollback-only异常，都失败
    // Required Requires_new，B抛异常A不捕捉，A、B都回滚；A捕捉B的异常，A成功，B回滚；B没抛异常，B就会直接提交，不会等到A提交；A在调用完B后抛异常，不会影响B
    // Required Nested，B抛异常A不捕捉，A、B都回滚；A捕捉B的异常，A成功，B回滚；B没抛异常，B不会提交，等着跟A一起提交，所以如果A在调用完B后抛异常，会回滚B




}
