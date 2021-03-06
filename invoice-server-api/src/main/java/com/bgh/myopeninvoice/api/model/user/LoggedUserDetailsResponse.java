/*
 * Copyright 2017 Branislav Cavlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bgh.myopeninvoice.api.model.user;

import com.bgh.myopeninvoice.api.model.response.OperationResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.Authentication;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoggedUserDetailsResponse extends OperationResponse {

    public LoggedUserDetailsResponse() {
    }

    public LoggedUserDetailsResponse(Authentication authentication) {
        this.authentication = authentication;
    }

    @ApiModelProperty(required = true, value = "")
    private Authentication authentication;
}
