package org.gdgac.android.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * GDG Aachen
 * org.gdgac.android.api.model
 * <p/>
 * User: maui
 * Date: 21.04.13
 * Time: 22:27
 */
public class Chapter implements Comparable<Chapter>, Parcelable {
    private String status, city, name, gplusId, chapterId, state, country;
    private Geo geo;

    public Chapter() {

    }

    public Chapter(Parcel in) {
        name = in.readString();
        status = in.readString();
        city = in.readString();
        gplusId = in.readString();
        chapterId = in.readString();
        state = in.readString();
        country = in.readString();
        geo = in.readParcelable(Geo.class.getClassLoader());
    }

    public String getStatus() {
        return status;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getGplusId() {
        return gplusId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public Geo getGeo() {
        return geo;
    }

    @Override
    public String toString() {
        return name.replaceAll("GDG ","");
    }

    @Override
    public int compareTo(Chapter o) {
        return name.compareTo(o.getName());
    }

    @Override
    public int describeContents() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(status);
        parcel.writeString(city);
        parcel.writeString(gplusId);
        parcel.writeString(chapterId);
        parcel.writeString(state);
        parcel.writeString(country);
        parcel.writeParcelable(geo,0);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Chapter createFromParcel(Parcel in) {
            return new Chapter(in);
        }

        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };

    @Override
    public boolean equals(Object o) {
       if(o == null)
           return false;

        if(o instanceof Chapter) {
            Chapter other = (Chapter)o;
            return other.getChapterId().equals(getChapterId());
        }

        return false;
    }
}
