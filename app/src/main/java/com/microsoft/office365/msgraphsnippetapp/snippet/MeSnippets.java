/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.office365.msgraphsnippetapp.snippet;

import com.google.gson.JsonObject;
import com.microsoft.graph.concurrency.ICallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.office365.msgraphsnippetapp.application.SnippetApp;

import static com.microsoft.office365.msgraphsnippetapp.R.array.get_me;
import static com.microsoft.office365.msgraphsnippetapp.R.array.get_me_direct_reports;
import static com.microsoft.office365.msgraphsnippetapp.R.array.get_me_group_membership;
import static com.microsoft.office365.msgraphsnippetapp.R.array.get_me_manager;
import static com.microsoft.office365.msgraphsnippetapp.R.array.get_me_photo;
import static com.microsoft.office365.msgraphsnippetapp.R.array.get_me_responsibilities;

public abstract class MeSnippets<Result> extends AbstractSnippet<Result> {
    /**
     * Snippet constructor
     *
     * @param descriptionArray The String array for the specified snippet
     */
    public MeSnippets(Integer descriptionArray) {
        super(SnippetCategory.meSnippetCategory, descriptionArray);
    }

    static MeSnippets[] getMeSnippets() {
        return new MeSnippets[]{
                // Marker element
                new MeSnippets(null) {
                    @Override
                    public void request(ICallback callback) {
                        // Not implemented
                    }
                },
                // Snippets

                /* Get information about signed in user
                 * HTTP GET https://graph.microsoft.com/{version}/me
                 * @see https://graph.microsoft.io/docs/api-reference/v1.0/api/user_get
                 */
                new MeSnippets<JsonObject>(get_me) {
                    @Override
                    public void request(final ICallback<JsonObject> callback) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                JsonObject result = null;

                                try {
                                    result = SnippetApp.getApp().getGraphServiceClient().getMe().buildRequest().get().getRawObject();
                                    callback.success(result);
                                } catch (ClientException clientException) {
                                    //callback.failure();
                                }
                            }
                        }).start();
                    }
                },

                /* Get responsibilities of signed in user
                 * HTTP GET https://graph.microsoft.com/{version}/me?$select=AboutMe,Responsibilities,Tags
                 * @see https://graph.microsoft.io/docs/api-reference/v1.0/resources/user
                 */
                new MeSnippets<JsonObject>(get_me_responsibilities) {
                    @Override
                    public void request(final ICallback<JsonObject> callback) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                JsonObject result = null;

                                // TODO: This call fails with a Bad request error.
                                // The SDK tries to make a request to https://graph.microsoft.com/v1.0/me?select=responsibilities
                                // Instead of https://graph.microsoft.com/v1.0/me?$select=responsibilities
                                // Note the $select clause is missing the '$'
                                try {
                                    result = SnippetApp.getApp().getGraphServiceClient().getMe().buildRequest().select("AboutMe,Responsibilities,Tags").get().getRawObject();
                                    callback.success(result);
                                } catch (ClientException clientException) {
                                    clientException.getCause();
                                }
                            }
                        }).start();
                    }
                },

                /* Get the user's manager
                 * HTTP GET https://graph.microsoft.com/{version}/me/manager
                 * @see https://graph.microsoft.io/docs/api-reference/v1.0/resources/user
                 */
                new MeSnippets<JsonObject>(get_me_manager) {
                    @Override
                    public void request(final ICallback<JsonObject> callback) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                JsonObject result = null;

                                try {
                                    result = SnippetApp.getApp().getGraphServiceClient().getMe().getManager().buildRequest().get().getRawObject();
                                    callback.success(result);
                                } catch (ClientException clientException) {
                                    clientException.getCause();
                                }
                            }
                        }).start();
                    }
                },

                /* Get the user's direct reports
                 * HTTP GET https://graph.microsoft.com/{version}/me/directReports
                 * @see https://graph.microsoft.io/docs/api-reference/v1.0/resources/user
                 */
                new MeSnippets<JsonObject>(get_me_direct_reports) {
                    @Override
                    public void request(final ICallback<JsonObject> callback) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                JsonObject result = null;

                                try {
                                    result = SnippetApp.getApp().getGraphServiceClient().getMe().getDirectReports().buildRequest().get().getRawObject();
                                    callback.success(result);
                                } catch (ClientException clientException) {
                                    //callback.failure();
                                }
                            }
                        }).start();
                    }
                },

                /* Get the group membership of the user
                 * HTTP GET https://graph.microsoft.com/{version}/me/memberOf
                 * @see https://graph.microsoft.io/docs/api-reference/v1.0/resources/user
                 */
                new MeSnippets<JsonObject>(get_me_group_membership) {
                    @Override
                    public void request(final ICallback<JsonObject> callback) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                JsonObject result = null;

                                try {
                                    // TODO: Feedback for SDK - getGetMemberGroups seems odd. Also, why do we have to use a post to retrieve data?
                                    result = SnippetApp.getApp().getGraphServiceClient().getMe().getMemberOf().buildRequest().get().getRawObject();
                                    callback.success(result);
                                } catch (ClientException clientException) {
                                    //callback.failure();
                                    clientException.getCause();
                                }
                            }
                        }).start();
                    }
                },

                /* Get the photo of the user
                 * HTTP GET https://graph.microsoft.com/{version}/me/userPhoto
                 * @see https://graph.microsoft.io/docs/api-reference/v1.0/resources/user
                 */
                new MeSnippets<JsonObject>(get_me_photo) {
                    @Override
                    public void request(final ICallback<JsonObject> callback) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                JsonObject result = null;

                                try {
                                    result = SnippetApp.getApp().getGraphServiceClient().getMe().getPhoto().buildRequest().get().getRawObject();
                                    callback.success(result);
                                } catch (ClientException clientException) {
                                    clientException.getCause();
                                }
                            }
                        }).start();
                    }
                }
        };
    }

    @Override
    public abstract void request(ICallback<Result> callback);

}