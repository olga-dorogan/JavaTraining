package com.custom.form;

import com.custom.domain.ParfumWithPrice;
import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.cmd.Query;

import static com.custom.service.OfyService.ofy;

/**
 * Created by olga on 21.06.15.
 */
public class ParfumProducerForm {
    public enum FieldType {
        STRING
    }

    public enum Field {
        PRODUCER(ParfumWithPrice.PRODUCER, FieldType.STRING);

        private String fieldName;
        private FieldType fieldType;

        Field(String fieldName, FieldType fieldType) {
            this.fieldName = fieldName;
            this.fieldType = fieldType;
        }

        private String getFieldName() {
            return this.fieldName;
        }
    }

    public enum Operator {
        EQ("=="),
        NE("!=");

        private String queryOperator;

        Operator(String queryOperator) {
            this.queryOperator = queryOperator;
        }

        private String getQueryOperator() {
            return this.queryOperator;
        }
    }

    public static class Filter {
        private Field field;
        private Operator operator;
        private String value;

        public Filter() {
        }

        public Filter(Field field, Operator operator, String value) {
            this.field = field;
            this.operator = operator;
            this.value = value;
        }

        public Field getField() {
            return field;
        }

        public Operator getOperator() {
            return operator;
        }

        public String getValue() {
            return value;
        }
    }

    private Filter filter;

    public ParfumProducerForm() {
    }

    public Filter getFilter() {
        return filter;
    }

    public ParfumProducerForm filter(Filter filter) {
        this.filter = filter;
        return this;
    }

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Query<ParfumWithPrice> getQuery() {
        Query<ParfumWithPrice> query = ofy().load().type(ParfumWithPrice.class);
        if (filter != null) {
            query = query.filter(
                    String.format("%s %s", filter.field.getFieldName(), filter.operator.getQueryOperator()),
                    filter.value);
            query = query.order(filter.field.getFieldName());
        }
        query = query.order(ParfumWithPrice.PRICE);
        return query;
    }
}
