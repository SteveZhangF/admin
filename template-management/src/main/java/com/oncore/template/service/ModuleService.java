/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oncore.template.service;

import com.oncore.template.model.Module;
import com.oncore.template.transfd.module.CreateModuleRequest;

/**
 * Created by steve on 1/26/16.
 */
public interface ModuleService extends IBaseGenericService<Module,String>{
    Module createModuleFromRequest(CreateModuleRequest request);
    Module getModule(String id);

    Module updateModuleFromRequest(String id, CreateModuleRequest request);

}
