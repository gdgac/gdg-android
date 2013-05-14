package org.gdgac.android.task;

import java.util.ArrayList;
import java.util.concurrent.Executor;

/**
 * GDG Aachen
 * org.gdgac.android.task
 * <p/>
 * User: maui
 * Date: 22.04.13
 * Time: 00:32
 */
public class Builder<Params, Result> {

    private CommonAsyncTask<Params, Result> mTask;
    private ArrayList<Params> mParams;

    public Builder(Class<Params> paramsType, Class<Result> resultType) {
        mTask = new CommonAsyncTask<Params, Result>(paramsType, resultType);
        mParams = new ArrayList<Params>();
    }

    public Builder<Params, Result> addParameter(Params param) {
        mParams.add(param);
        return this;
    }

    public Builder<Params, Result> setParameter(ArrayList<Params> params) {
        mParams = params;
        return this;
    }

    public Builder<Params, Result> setOnPostExecuteListener(CommonAsyncTask.OnPostExecuteListener<Result> listener) {
        mTask.setPostListener(listener);
        return this;
    }

    public Builder<Params, Result> setOnBackgroundExecuteListener(CommonAsyncTask.OnBackgroundExecuteListener<Params, Result> listener) {
        mTask.setBackgroundListener(listener);
        return this;
    }

    public Builder<Params, Result> setOnPreExecuteListener(CommonAsyncTask.OnPreExecuteListener listener) {
        mTask.setPreListener(listener);
        return this;
    }

    public CommonAsyncTask<Params, Result> build() {
        return mTask;
    }

    public void buildAndExecute() {
        build().execute((Params[])mParams.toArray());
    }

    public void buildAndExecuteOnExecutor(Executor executor) {
        build().executeOnExecutor(executor, (Params[])mParams.toArray());
    }
}