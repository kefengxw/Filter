package com.filter.android.di.component

import com.filter.android.di.module.HomeActivityModule
import com.filter.android.di.scope.ActivityScope
import com.filter.android.view.HomeActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [HomeActivityModule::class])
interface HomeActivityComponent {

    fun inject(homeActivity: HomeActivity)

    //Activity getActivity();
    //Context getCountryActivityContext();

    @Subcomponent.Builder
    interface Builder {

        //@BindsInstance
        //activity(Activity activity): Builder    //same as an module with parameter, can simplify the ActivityModule

        fun homeActivityModule(homeActivityModule: HomeActivityModule): Builder //this is more understandable
        fun build(): HomeActivityComponent        //only need this for sub component, that's all
    }
}