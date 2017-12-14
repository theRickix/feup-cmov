using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;
using WeatherApp.ViewModels;

namespace WeatherApp
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class Favourites : ContentPage
    {
        public Favourites()
        {
            InitializeComponent();
            BindingContext = new FavouritesViewModel();

            ListFav.ItemTapped += (object sender, ItemTappedEventArgs e) => {
                if (e.Item == null) return;
                ((ListView)sender).SelectedItem = null;
            };
        }

        protected override void OnAppearing()
        {
            base.OnAppearing();

            ((FavouritesViewModel)BindingContext).OnAppearing();
        }
    }

}