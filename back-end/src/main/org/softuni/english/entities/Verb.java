package org.softuni.english.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "verbs")
public class Verb {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    private String firstForm;

    private String secondForm;

    private String thirdForm;

    private String translate;

    public Verb() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstForm() {
        return firstForm;
    }

    public void setFirstForm(String firstForm) {
        this.firstForm = firstForm;
    }

    public String getSecondForm() {
        return secondForm;
    }

    public void setSecondForm(String secondForm) {
        this.secondForm = secondForm;
    }

    public String getThirdForm() {
        return thirdForm;
    }

    public void setThirdForm(String thirdForm) {
        this.thirdForm = thirdForm;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

}
