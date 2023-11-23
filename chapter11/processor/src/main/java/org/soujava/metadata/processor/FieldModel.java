package org.soujava.metadata.processor;

import java.util.List;

public class FieldModel extends BaseMappingModel {

    private final String packageName;
    private final String name;
    private final String type;
    private final String entity;
    private final String reader;
    private final String writer;
    private final String fieldName;
    private final boolean id;
    private final List<String> arguments;

    FieldModel(String packageName, String name,
               String type, String entity,
               String reader, String writer, String fieldName,
               boolean id,
               List<String> arguments) {
        this.packageName = packageName;
        this.name = name;
        this.type = type;
        this.entity = entity;
        this.reader = reader;
        this.writer = writer;
        this.fieldName = fieldName;
        this.id = id;
        this.arguments = arguments;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getEntity() {
        return entity;
    }

    public String getReader() {
        return reader;
    }

    public String getWriter() {
        return writer;
    }

    public String getQualified() {
        return packageName + "." + getClassName();
    }

    public String getClassName() {
        return entity + ProcessorUtil.capitalize(fieldName) + "FieldMetaData";
    }

    public String getFieldName() {
        return fieldName;
    }

    public boolean isId() {
        return id;
    }

    public List<String> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "FieldModel{" +
                "packageName='" + packageName + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", entity='" + entity + '\'' +
                ", reader='" + reader + '\'' +
                ", writer='" + writer + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", id=" + id +
                '}';
    }

    public static FieldMetaDataBuilder builder() {
        return new FieldMetaDataBuilder();
    }


    public static class FieldMetaDataBuilder {
        private String packageName;
        private String name;
        private String type;
        private String entity;
        private String reader;
        private String writer;
        private String fieldName;
        private boolean id;
        List<String> arguments;

        private FieldMetaDataBuilder() {
        }

        public FieldMetaDataBuilder withPackageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public FieldMetaDataBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public FieldMetaDataBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public FieldMetaDataBuilder withEntity(String entity) {
            this.entity = entity;
            return this;
        }

        public FieldMetaDataBuilder withReader(String getName) {
            this.reader = getName;
            return this;
        }

        public FieldMetaDataBuilder withWriter(String setName) {
            this.writer = setName;
            return this;
        }

        public FieldMetaDataBuilder withFieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public FieldMetaDataBuilder withId(boolean id) {
            this.id = id;
            return this;
        }

        public FieldMetaDataBuilder withArguments(List<String> arguments) {
            this.arguments = arguments;
            return this;
        }

        public FieldModel build() {
            return new FieldModel(packageName, name, type, entity, reader, writer, fieldName, id, arguments);
        }
    }
}
