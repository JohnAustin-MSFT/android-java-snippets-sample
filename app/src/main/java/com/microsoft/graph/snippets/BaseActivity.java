/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.graph.snippets;

import com.microsoft.graph.auth.AzureADModule;
import com.microsoft.graph.auth.AzureAppCompatActivity;
import com.microsoft.graph.snippets.application.SnippetApp;
import com.microsoft.graph.snippets.inject.AzureModule;
import com.microsoft.graph.snippets.inject.ObjectGraphInjector;

import dagger.ObjectGraph;

public abstract class BaseActivity
        extends AzureAppCompatActivity
        implements ObjectGraphInjector {

    @Override
    protected AzureADModule getAzureADModule() {
        AzureADModule.Builder builder = new AzureADModule.Builder(this);
        builder.validateAuthority(true)
                .authenticationResourceId(ServiceConstants.AUTHENTICATION_RESOURCE_ID)
                .authorityUrl(ServiceConstants.AUTHORITY_URL)
                .redirectUri(ServiceConstants.REDIRECT_URI)
                .clientId(ServiceConstants.CLIENT_ID);
        return builder.build();
    }

    @Override
    protected Object[] getModules() {
        return new Object[]{new AzureModule()};
    }

    @Override
    protected ObjectGraph getRootGraph() {
        return SnippetApp.getApp().mObjectGraph;
    }

    @Override
    public void inject(Object target) {
        mObjectGraph.inject(target);
    }
}