/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.codegen;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author reemeeka
 */
@Configuration
public class CodeGenConfig {

    @Bean
    public TemplateServiceImpl getTemplateServiceImpl() {
        return new TemplateServiceImpl();
    }

    @Bean
    public FileTemplateJavaBean getFileTemplateJavaBean() {
        return new FileTemplateJavaBean();
    }

}
