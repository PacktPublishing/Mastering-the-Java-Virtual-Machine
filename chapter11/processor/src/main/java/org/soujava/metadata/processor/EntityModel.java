package org.soujava.metadata.processor;

import java.util.List;

public class EntityModel extends BaseMappingModel {

    private final String packageName;

    private final String entity;

    private final String name;

    private final List<String> fields;

    public EntityModel(String packageName, String entity, String name, List<String> fields) {
        this.packageName = packageName;
        this.entity = entity;
        this.name = name;
        this.fields = fields;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getEntity() {
        return entity;
    }

    public String getEntityQualified() {
        return packageName + '.' + entity;
    }

    public String getClassName() {
        return entity + "EntityMetaData";
    }

    public String getQualified() {
        return packageName + "." + getClassName();
    }

    public String getName() {
        return name;
    }

    public List<String> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "EntityMetadata{" +
                "packageName='" + packageName + '\'' +
                ", sourceClassName='" + entity + '\'' +
                ", entityName='" + name + '\'' +
                '}';
    }
}
