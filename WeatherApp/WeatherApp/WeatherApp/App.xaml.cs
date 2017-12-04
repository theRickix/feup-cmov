using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Xamarin.Forms;

namespace WeatherApp
{
    public partial class App : Application
    {
        static WeatherDatabase database;
        public App()
        {
            InitializeComponent();

            MainPage = new WeatherApp.MainPage();
        }
        public static WeatherDatabase Database
        {
            get
            {
                if (database == null)
                {
                    database = new WeatherDatabase(DependencyService.Get<IFileHelper>().GetLocalFilePath("TodoSQLite.db3"));
                }
                return database;
            }
        }
    }
}
