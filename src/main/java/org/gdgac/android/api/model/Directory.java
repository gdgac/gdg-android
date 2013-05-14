package org.gdgac.android.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * GDG Aachen
 * org.gdgac.android.api.model
 * <p/>
 * User: maui
 * Date: 21.04.13
 * Time: 22:30
 */
public class Directory implements Response, Parcelable {
    private ArrayList<Chapter> groups;

    public Directory() {
        groups = new ArrayList<Chapter>();
    }

    public Directory(Parcel in) {
        groups = new ArrayList<Chapter>();
        in.readTypedList(groups, Chapter.CREATOR);
    }

    public ArrayList<Chapter> getGroups() {
        return groups;
    }

    @Override
    public int describeContents() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(groups);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Chapter createFromParcel(Parcel in) {
            return new Chapter(in);
        }

        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };
}
