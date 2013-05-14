package org.gdgac.android.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * GDG Aachen
 * org.gdgac.android.api.model
 * <p/>
 * User: maui
 * Date: 21.04.13
 * Time: 22:28
 */
public class Geo implements Parcelable {
    private double lat, lng;

    public Geo() {

    }

    public Geo(Parcel in) {
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    @Override
    public int describeContents() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Geo createFromParcel(Parcel in) {
            return new Geo(in);
        }

        public Geo[] newArray(int size) {
            return new Geo[size];
        }
    };
}
