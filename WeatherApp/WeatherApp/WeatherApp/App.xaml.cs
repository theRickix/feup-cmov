﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Xamarin.Forms;

namespace WeatherApp
{
    public partial class App : Application
    {
        public static NavigationPage NavPage = new NavigationPage(new WeatherApp.Navigation());
        public App()
        {
            InitializeComponent();
            MainPage = new Navigation();
        }

        protected override void OnStart()
        {

        }

        protected override void OnSleep()
        {
            // Handle when your app sleeps
        }

        protected override void OnResume()
        {
            // Handle when your app resumes
        }
    }
}
