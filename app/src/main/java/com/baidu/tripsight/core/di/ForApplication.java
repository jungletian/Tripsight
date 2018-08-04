/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.di;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Application Context 限定符
 *
 * @author zhangtianjun
 */
@Qualifier
@Retention(RUNTIME)
public @interface ForApplication {
}
