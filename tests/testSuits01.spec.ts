import { test, expect } from '@playwright/test';

test.describe('Test suite backend V1', () => {
  
  test('Test case 01 - Get all customers', async ({ request }) => {
    const getPostsResponse = await request.get('http://localhost:9090/api/v1/customers');
    expect(getPostsResponse.ok()).toBeTruthy(); 
    expect(getPostsResponse.status()).toBe(200);
  });

  test('Test case 02 - Create orders', async ({ request }) => {
    const createPostsResponse = await request.post('http://localhost:9090/api/v1/ordercar', {
      data: {
        "userId": 2,
        "carId": 3,
        "date": "2024-09-30T09:25:48.231Z",
        "numberOfDays": 0
      }
    });
  
    expect(createPostsResponse.ok()).toBeTruthy();
  });
  
  
  test('Test case 03 - Get all cars', async ({ request }) => {
    const getPostsResponse = await request.get('http://localhost:9090/api/v1/allcars');
    expect(getPostsResponse.ok()).toBeTruthy(); 
    expect(getPostsResponse.status()).toBe(200);
  });
  
  
  test('Test case 04 - Delete Post by ID', async ({ request }) => {
    const getPostsResponse = await request.get('http://localhost:9090/api/v1/customers');
    expect(getPostsResponse.ok()).toBeTruthy();
    const allPosts = await getPostsResponse.json();
    expect(allPosts.lenght).toBeGreaterThan(3);
    
    const lastButOnePostID = allPosts[allPosts.length - 2].id;

    //Delete req
    const deletePostResponse = await request.delete(`http://localhost:9090/api/v1/customers/${lastButOnePostID}`);
    
    expect (deletePostResponse.ok()).toBeTruthy();

    //Verify the delete
    const deleteElementResponse = await request.get(`http://localhost:9090/api/v1/customers/${lastButOnePostID}`);
    expect (deleteElementResponse.status()).toBe(404)
  });
})

