import { test, expect } from '@playwright/test';

test.describe('Test suite backend V1', () => {
  test('test case 01 - get all customers', async ({ request }) => {
    const getPostsResponse = await request.get('http://localhost:9090/api/v1/customers');
    expect(getPostsResponse.ok()).toBeTruthy(); 
  });
});
