/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.pinot.server.api.resources;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.helix.HelixManager;
import org.apache.helix.model.InstanceConfig;
import org.apache.pinot.common.utils.helix.HelixHelper;
import org.apache.pinot.server.api.AdminApiApplication;

@Path("/instance")
public class InstanceTagFetcher {
    @Inject
    @Named(AdminApiApplication.SERVER_INSTANCE_ID)
    private String _instanceId;
    @Inject
    private HelixManager _helixManager;

    @GET
    @Path("/tags")
    @ApiOperation(value = "Tenant tags for current instance")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Table not found")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getInstanceTags() {
        InstanceConfig config = HelixHelper.getInstanceConfig(_helixManager, _instanceId);
        return config.getTags();
    }
}
