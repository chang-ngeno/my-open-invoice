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

package com.bgh.myopeninvoice.jsf.jsfbeans;

import org.omnifaces.util.Faces;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by bcavlin on 15/03/17.
 */
@ManagedBean
@ApplicationScoped
@Component
@PropertySource("classpath:version.properties")
public class ApplicationBean implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(ApplicationBean.class);

    @Value("${application.version}")
    private String applicationVersion;

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public void logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/logout");
    }

    public String getVersion() {
        StringBuilder b = new StringBuilder();
        b.append(FacesContext.class.getPackage().getImplementationTitle()).append("/");
        b.append(FacesContext.class.getPackage().getImplementationVersion()).append(", ");
        b.append(Faces.getServerInfo()).append(", ");
        b.append("PrimeFaces/");
        b.append(RequestContext.getCurrentInstance().getApplicationContext().getConfig().getBuildVersion());

        return b.toString();
    }

}
