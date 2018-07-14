package org.softuni.english.models.BindingModels;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class VerbCreateBindingModel {
    private static final int MIN_VERB_LENGTH = 2;
    private static final int MAX_VERB_LENGTH = 20;
    private static final int MIN_TRANSLATE_LENGTH = 2;
    private static final int MAX_VERB_TRANSLATE_LENGTH = 500;


    private String id;

    @NotEmpty
    @Size(min = MIN_VERB_LENGTH,max = MAX_VERB_LENGTH)
    private String firstForm;

    @NotEmpty
    @Size(min = MIN_VERB_LENGTH,max = MAX_VERB_LENGTH)
    private String secondForm;

    @NotEmpty
    @Size(min = MIN_VERB_LENGTH,max = MAX_VERB_LENGTH)
    private String thirdForm;

    @NotEmpty
    @Size(min = MIN_TRANSLATE_LENGTH,max = MAX_VERB_TRANSLATE_LENGTH)
    private String translate;

    public VerbCreateBindingModel() {
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
