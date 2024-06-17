# Wise Coin

This Android application integrates with the Monobank API to fetch and display user transaction data. User autorize using a QR code or direct link, allowing to easily retrieve personal token for further API interactions.


## Features
- Scan or click a QR code to authenticate with Monobank;
- Display the Monobank web page within the app for seamless user experience;
- Retrieve and store user transaction data using the personal token;
- Add ability to create own transactions;
- Utilize local database to save and retrieve transaction data;
- Display incomes and expenses on pie charts.

## App flows

### Authorization Process
The app allows users to authenticate with Monobank to fetch and display their transaction data. Users also have the option to skip the authentication and use the app without Monobank data.

- **Welcome Screen**: On the welcome screen, users can choose to "Continue with Monobank" or skip the authentication process to use the app without Monobank data.
- **QR Code Screen**: If users choose to authenticate, they will see a QR code on the next screen. Users can scan or click the QR code, which will automatically open the Monobank app.
- **Monobank Authorization**: In the Monobank app, users must click "Allow" to grant access. After granting access, the Monobank app will close automatically, and users will be redirected back to the app.
- **Token Activation**: Users will see a screen with an "Activate" button. By clicking "Activate", users can retrieve their personal token. The token will be automatically extracted and used to fetch transaction data.
- **Error Handling**: If an error occurs during this process, a "Login with token" button will appear, allowing users to manually enter the token.

Screenshot:

<img width="847" alt="Screenshot 2024-06-17 at 09 06 12" src="https://github.com/KontVIP/Wise_Coin/assets/76660306/56619899-4798-4c51-8084-760411df16b5">

### Home screen overview
Whether users skip the authentication process or successfully authorize with Monobank, they can view their incomes and expenses in a clear and intuitive pie chart. This section provides a comprehensive overview of financial transactions, categorized for easy analysis and management.

- Pie Chart Display:
  - The main screen shows a pie chart that visualizes the distribution of incomes and expenses.
  - Users can switch between viewing expenses and incomes.
- Category Details:
  - Users can click on any category within the pie chart to see a detailed list of transactions for that category.
  - This allows users to delve deeper into their spending and income patterns.
- Manage Transactions:
  - Users have the option to manage their transactions directly within the app.
  - By clicking on a specific transaction, users can choose to remove it if needed.
 
Screenshot:

<img width="595" alt="Screenshot 2024-06-17 at 09 15 31" src="https://github.com/KontVIP/Wise_Coin/assets/76660306/bb5c0171-112c-4724-99cc-7607bb03dcec">


### Creating a Local Transaction
Users can easily create local transactions within the app, allowing them to keep track of their finances even if they have not authenticated with Monobank. The process is straightforward and includes several customizable fields to ensure accurate data entry.

- Accessing the Add Transaction Screen:
  - From the main screen, click on the Floating Action Button (FAB) to open the "Add transaction" screen.

- Entering Transaction Details:

  - Category: Write the category of the transaction. The category field has an autofill feature to help users quickly find and select existing categories.
  - Expense or Income: Select whether the transaction is an expense or an income.
  - Amount: Enter the price of the transaction.
  - Description: Add a description for the transaction to provide additional context.

- Additional Features:
  - **Image Upload**: Users can load an image related to the transaction, such as a receipt or invoice, by clicking on the image icon.
  - **Date and Time*: Set the exact date and time of the transaction by clicking the "Set Time" button. This allows for precise tracking of when each transaction occurred.

- Saving the Transaction:
  - Once all fields are filled out, click the "Create" button to save the transaction. The new transaction will be added to the local database and displayed in the appropriate category.

Screenshot:

<img width="845" alt="Screenshot 2024-06-17 at 09 18 43" src="https://github.com/KontVIP/Wise_Coin/assets/76660306/03c01227-603d-4ee1-b4c1-706e4794b359">

### History screen
The History screen provides users with a list of all their transactions, organized from newest to oldest. This feature enables users to quickly review and manage their transaction history.

- Transaction List:
  - All transactions are displayed in a chronological list, with the most recent transactions at the top.
  - Each item in the list includes essential details such as the category, amount, and date of the transaction.
- Viewing Transaction Details:
  - Users can click on any transaction item to view more details about the transaction.
- Deleting Transactions:
  - If a user needs to remove a transaction, they can click on the specific transaction item and select the "Remove" option.
  - This action will delete the transaction from the local database, ensuring the history remains up-to-date and accurate.

Screenshot:

<img width="272" alt="Screenshot 2024-06-17 at 09 21 41" src="https://github.com/KontVIP/Wise_Coin/assets/76660306/58488db2-1449-474a-83ee-6cd916bc376b">


### Menu screen
The Menu screen is the central hub for managing user settings and preferences within the app. It provides various options for users to customize their experience and manage their authentication tokens.

- User Information:
  - The top of the Menu screen displays the user's name, providing a personalized touch to the app.
- Token Management:
  - **Remove Token**: If a token is already added, users can click this button to remove the current token.
  - **Change Token**: If a token is already added, users can click this button to replace the existing token with a new one.
  - **Add Token**: If no token is added, users will see this button to add a new token for authentication.
- Currency Selection:
  - **Change Currency**: This button allows users to select their preferred currency from options such as USD, Euro, and UAH.
  - The selected currency will be used throughout the app to display transaction amounts.

Screenshot:

<img width="609" alt="Screenshot 2024-06-17 at 09 24 51" src="https://github.com/KontVIP/Wise_Coin/assets/76660306/b6f1ae16-a00e-4934-9daa-cf33b4c560a3">


## Tech Stack
- Kotlin;
- Hilt;
- Coroutines;
- MPAndroidChart;
- LiveData;
- Room;
- Retrofit
- Clean Architecture;
- MVVM.

### Monobank API
https://api.monobank.ua/docs/index.html

## Note
Some parts of this application are in Ukrainian language due to its integration with a Ukrainian bank's services. The server returns Ukrainian texts for certain features and messages. If you encounter Ukrainian text and need assistance, feel free to reach out or use translation tools for clarification.
