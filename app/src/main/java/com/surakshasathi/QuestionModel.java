package com.surakshasathi;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class QuestionModel implements Parcelable {

    private String question;
    private List<String> options;
    private String correct;

    public QuestionModel(String question, List<String> options, String correct) {
        this.question = question;
        this.options = options;
        this.correct = correct;
    }

    public QuestionModel() {
        this("", null, "");
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrect() {
        return correct;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(question);
        parcel.writeStringList(options);
        parcel.writeString(correct);
    }

    public static final Parcelable.Creator<QuestionModel> CREATOR = new Parcelable.Creator<QuestionModel>() {
        @Override
        public QuestionModel createFromParcel(Parcel in) {
            return new QuestionModel(in);
        }

        @Override
        public QuestionModel[] newArray(int size) {
            return new QuestionModel[size];
        }
    };

    private QuestionModel(Parcel in) {
        question = in.readString();
        options = in.createStringArrayList();
        correct = in.readString();
    }
}
