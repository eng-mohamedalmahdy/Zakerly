package com.graduationproject.zakerly.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;


public class Instructor extends RealmObject implements Parcelable {

    private User user;

    private double pricePerHour;
    private int ratesCount;
    private int rateSum;
    private double rate;
    private String bio;
    private String title;
    private int numberOfStudents;
    private int averageRate;
    private int fiveStarCount;
    private int fourStarCount;
    private int threeStarCount;
    private int twoStarCount;
    private int oneStarCount;

    public Instructor() {
    }

    public Instructor(User user, double pricePerHour) {
        this.user = user;
        this.pricePerHour = pricePerHour;
        this.rate = 5.0;
        this.bio = "";
    }


    protected Instructor(Parcel in) {
        pricePerHour = in.readDouble();
        ratesCount = in.readInt();
        rateSum = in.readInt();
        rate = in.readDouble();
        bio = in.readString();
        title = in.readString();
        numberOfStudents = in.readInt();
        averageRate = in.readInt();
        fiveStarCount = in.readInt();
        fourStarCount = in.readInt();
        threeStarCount = in.readInt();
        twoStarCount = in.readInt();
        oneStarCount = in.readInt();
    }

    public static final Creator<Instructor> CREATOR = new Creator<Instructor>() {
        @Override
        public Instructor createFromParcel(Parcel in) {
            return new Instructor(in);
        }

        @Override
        public Instructor[] newArray(int size) {
            return new Instructor[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public int getAverageRate() {
        return ratesCount == 0 ? 0 : rateSum / ratesCount;
    }

    public void setAverageRate(int averageRate) {
        this.averageRate = averageRate;
    }

    public int getFiveStarCount() {
        return fiveStarCount;
    }

    public void setFiveStarCount(int fiveStarCount) {
        this.fiveStarCount = fiveStarCount;
    }

    public int getFourStarCount() {
        return fourStarCount;
    }

    public void setFourStarCount(int fourStarCount) {
        this.fourStarCount = fourStarCount;
    }

    public int getThreeStarCount() {
        return threeStarCount;
    }

    public void setThreeStarCount(int threeStarCount) {
        this.threeStarCount = threeStarCount;
    }

    public int getTwoStarCount() {
        return twoStarCount;
    }

    public void setTwoStarCount(int twoStarCount) {
        this.twoStarCount = twoStarCount;
    }

    public int getOneStarCount() {
        return oneStarCount;
    }

    public void setOneStarCount(int oneStarCount) {
        this.oneStarCount = oneStarCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getRatesCount() {
        return ratesCount;
    }

    public void setRatesCount(int ratesCount) {
        this.ratesCount = ratesCount;
    }

    public int getRateSum() {
        return rateSum;
    }

    public void setRateSum(int rateSum) {
        this.rateSum = rateSum;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "user=" + user +
                ", pricePerHour=" + pricePerHour +
                ", rate=" + rate +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instructor)) return false;

        Instructor that = (Instructor) o;

        return user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(pricePerHour);
        dest.writeInt(ratesCount);
        dest.writeInt(rateSum);
        dest.writeDouble(rate);
        dest.writeString(bio);
        dest.writeString(title);
        dest.writeInt(numberOfStudents);
        dest.writeInt(averageRate);
        dest.writeInt(fiveStarCount);
        dest.writeInt(fourStarCount);
        dest.writeInt(threeStarCount);
        dest.writeInt(twoStarCount);
        dest.writeInt(oneStarCount);
    }
}
