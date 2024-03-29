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
package io.transaction.tcc.bank01.controller;

import io.transaction.tcc.bank01.service.UserAccountBank01Service;
import io.transaction.tcc.common.dto.UserAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author binghe
 * @version 1.0.0
 * @description 转账操作的Controller
 */
@RestController
public class TransferController {
    @Autowired
    private UserAccountBank01Service userAccountBank01Service;
    @PostMapping(value = "/transfer")
    public String transfer(@RequestParam("sourceAccountNo") String sourceAccountNo,
                           @RequestParam("targetAccountNo") String targetAccountNo,
                           @RequestParam("amount")BigDecimal amount){
        UserAccountDto userAccountDto = new UserAccountDto(UUID.randomUUID().toString(), "1001", "1002", BigDecimal.valueOf(100));
        userAccountBank01Service.transferAmount(userAccountDto);
        return "success";
    }
}
