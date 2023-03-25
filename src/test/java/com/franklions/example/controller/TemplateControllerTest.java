package com.franklions.example.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author flsh
 * @version 1.0
 * @date 2023/3/25
 * @since Jdk 1.8
 */
class TemplateControllerTest {

    @Test
    void createTemplate() {
    }


    @Test
    void saveAndUpdateTemplate() {
        /**
         * PUT http://localhost:10080/v0/api/template/save
         * {
         *         "templateId":"26c37e9ddfa0400db0b5339a6257571e",
         *         "name":"测试主键重复5",
         *         "isAdmin":true,
         *         "status":"1",
         *         "dictType":"abc"
         * }
         */
    }

    @Test
    void batchCreateTemplate() {
    }

    @Test
    void batchSaveTemplate() {
        /**
         * POST http://localhost:10080/v0/api/template/batch/save
         * [
         *     {
         *         "templateId":"f07448874dd9488f9a9044f4f48fbe3a",
         *         "name":"测试3",
         *         "isAdmin":true,
         *         "status":"1",
         *         "dictType":"abc"
         *     },
         *     {
         *         "name":"测试7",
         *         "isAdmin":true,
         *         "status":"1",
         *         "dictType":"abc"
         *     },
         *     {
         *         "name":"测试8",
         *         "isAdmin":true,
         *         "status":"1",
         *         "dictType":"abc"
         *     }
         * ]
         */
    }

    @Test
    void editTemplate() {
    }

    @Test
    void removeTemplate() {
    }

    @Test
    void loadOne() {
    }

    @Test
    void loadTemplateList() {
    }

    @Test
    void loadTemplatePage() {
    }
}