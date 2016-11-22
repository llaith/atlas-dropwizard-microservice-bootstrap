package tkt.atlas.helloworld.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 *
 */
public class Person {

    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String updatedBy;

    @JsonProperty
    private Date updatedAt;

    public Person() {
        super();
    }

    public Person(final String name) {

        this();

        this.name = name;

    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
    
}
