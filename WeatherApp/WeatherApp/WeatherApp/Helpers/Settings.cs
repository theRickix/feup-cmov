// Helpers/Settings.cs
using Plugin.Settings;
using Plugin.Settings.Abstractions;

namespace WeatherApp.Helpers
{
	/// <summary>
	/// This is the Settings static class that can be used in your Core solution or in any
	/// of your client applications. All settings are laid out the same exact way with getters
	/// and setters. 
	/// </summary>
	public static class Settings
	{
		private static ISettings AppSettings
		{
			get
			{
				return CrossSettings.Current;
			}
		}

		#region Setting Constants

		private const string SettingsKey = "settings_key";
		private static readonly string SettingsDefault = string.Empty;

		#endregion


		public static string GeneralSettings
		{
			get
			{
				return AppSettings.GetValueOrDefault(SettingsKey, SettingsDefault);
			}
			set
			{
				AppSettings.AddOrUpdateValue(SettingsKey, value);
			}
		}

        #region Setting Constants

        private const string AllCitiesKey = "allcities_key";
        private static readonly string AllCitiesDefault = "Aveiro,Beja,Braga,Bragança,Castelo Branco,Coimbra,Évora,Faro,Guarda,Leiria,Lisboa,Portalegre,Porto,Santarém,Setúbal,Viana do Castelo,Vila Real,Viseu";

        #endregion


        public static string AllCitiesSettings
        {
            get
            {
                return AppSettings.GetValueOrDefault(AllCitiesKey, AllCitiesDefault);
            }
            set
            {
                AppSettings.AddOrUpdateValue(AllCitiesKey, value);
            }
        }

        #region Setting Constants

        private const string FavouriteCitiesKey = "favouritecities_key";
        private static readonly string FavouriteCitiesDefault = "Aveiro,Beja,Braga,Bragança,Castelo Branco,Coimbra,Évora,Faro,Guarda,Leiria,Lisboa,Portalegre,Porto,Santarém,Setúbal,Viana do Castelo,Vila Real,Viseu";

        #endregion


        public static string FavouriteCitiesSettings
        {
            get
            {
                return AppSettings.GetValueOrDefault(FavouriteCitiesKey, FavouriteCitiesDefault);
            }
            set
            {
                AppSettings.AddOrUpdateValue(FavouriteCitiesKey, value);
            }
        }

    }
}