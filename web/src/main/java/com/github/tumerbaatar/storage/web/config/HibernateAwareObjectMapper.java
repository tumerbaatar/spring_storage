package com.github.tumerbaatar.storage.web.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

public class HibernateAwareObjectMapper extends ObjectMapper {

        public HibernateAwareObjectMapper() {
            registerModule(new Hibernate5Module());
        }
    }