using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SQLite;

namespace WeatherApp
{
    public class WeatherDatabase
    {
        SQLiteAsyncConnection database;

        public WeatherDatabase(string dbPath)
        {
            database = new SQLiteAsyncConnection(dbPath);
            database.CreateTableAsync<City>().Wait();
        }


        //cenas que me foram aconselhadas para o caso de ser necessário buscar segundo a documentação
        public Task<List<City>> GetItemsAsync()
        {
            return database.Table<City>().ToListAsync();
        }
        //buscar as cidades q o user está interessado
        public Task<List<City>> GetItemsNotDoneAsync()
        {
            return database.QueryAsync<City>("SELECT * FROM [City] WHERE [Favorite] = 0");
        }

        public Task<City> GetItemAsync(int id)
        {
            return database.Table<City>().Where(i => i.ID == id).FirstOrDefaultAsync();
        }

        public Task<int> SaveItemAsync(City item)
        {
            if (item.ID != 0)
            {
                return database.UpdateAsync(item);
            }
            else
            {
                return database.InsertAsync(item);
            }
        }

        public Task<int> DeleteItemAsync(City item)
        {
            return database.DeleteAsync(item);
        }
    }
}
