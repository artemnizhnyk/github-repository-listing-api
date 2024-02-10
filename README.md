# github-repository-listing-api

This is a repository for the Github Repository Listing api.

## Prerequisites

Make sure you have auth token for GitHub.
It's only 60 requests per our available without authorization.
Application use more than 60 requests to get the result.
Create token you can [here](https://github.com/settings/tokens),
You don't need to define any accesses for thi token.

## How to Run

### 1. Clone the Repository
```bash
git clone https://github.com/artemnizhnyk/github-repository-listing-api.git 
```

### 2. Start the application

### 3. Open your browser and enter the following link:
```link
http://localhost:8080/api/v1/github/{username}/repositories?authToken={YOUR_TOKEN}
```

Write the username of a user you want to look up instead of {username} 
Write your auth token instead of {YOUR_TOKEN}

***
You also can write your token in .env file, 
its located at the classpath of the project.

Than your browser link looks like:
```link
http://localhost:8080/api/v1/github/{username}/repositories
```
***

### 4. After you perform your request wait a minute and you have your result