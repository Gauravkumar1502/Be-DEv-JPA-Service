{
    "id": 1,
    "title": "Two Sum",
    "difficulty": "EASY",
    "tags": "Array, Hash Table",
    "companies": "Google, Facebook, Amazon, Microsoft, Apple, Bloomberg, Uber, Adobe",
    "description": "Given an array of integers `nums` and an integer `target`, return indices of the two numbers such that they add up to `target`.\nYou may assume that each input would have exactly one solution, and you may not use the same element twice.\nYou can return the answer in any order.",
    "constraints": "`2 <= nums.length <= 10^4`\n`-10^9 <= nums[i] <= 10^9`\n`-10^9 <= target <= 10^9`\n**Only one valid answer exists.**",
    "javaBoilerplateCode": "class Solution {\n    public int[] twoSum(int[] nums, int target) {\n        // Your code here\n    }\n}",
    "c11BoilerplateCode": "#include <stdio.h>\n#include <stdlib.h>\n\nint* twoSum(int* nums, int numsSize, int target, int* returnSize){\n    // Your code here\n}",
    "cppBoilerplateCode": "#include <vector>\n#include <unordered_map>\n\nstd::vector<int> twoSum(std::vector<int>& nums, int target) {\n    // Your code here\n}",
    "pythonBoilerplateCode": "class Solution:\n    def twoSum(self, nums: List[int], target: int) -> List[int]:\n        # Your code here",
    "defaultInputs": "[2,7,11,15]\n9\n[3,2,4]\n6\n[3,3]\n6",
    "javaCode": "import java.util.*;\npublic class Problem1{\n            public static int[] twoSum(int[] nums, int target) {\n                Map<Integer, Integer> map = new HashMap<>();\n                for (int i = 0; i < nums.length; i++) {\n                    int complement = target - nums[i];\n                    if (map.containsKey(complement)) {\n                        return new int[] { map.get(complement), i };\n            }\n            map.put(nums[i], i);\n        }\n        throw new IllegalArgumentException(\"No two sum solution\");\n    }\n    public static void main(String[] args) {\n                try (Scanner reader = new Scanner(System.in)) {\n                    // read from console while there is input\n                    while (reader.hasNext()) {\n                        int[] nums = Arrays.stream(reader.nextLine().replace(\"[\", \"\").replace(\"]\", \"\").split(\",\")).mapToInt(Integer::parseInt).toArray();\n                int target = Integer.parseInt(reader.nextLine());\n                validateTestCase(nums, target);\n            }\n        }\n    }\n    public static void validateTestCase(int[] nums, int target) {\n                int[] expected = twoSum(nums, target);\n                int[] userOutput = new Solution().twoSum(nums, target);\n                System.out.println((\"Nums= \"+Arrays.toString(nums)+\"/nTarget= \"+target) + \"::\" + Arrays.toString(userOutput) + \"::\" + Arrays.toString(expected));\n    }\n}",
    "createdAt": "2024-04-08T16:04:37.000+00:00",
    "updatedAt": "2024-04-08T16:04:37.000+00:00",
    "extraInfo": "**Follow-up:** Can you come up with an algorithm that is less than `O(n2)` time complexity?",
    "hints": [
        {
            "id": 54,
            "hint": "A really brute force way would be to search for all possible pairs of numbers but that would be too slow. Again, it's best to try out brute force solutions for just for completeness. It is from these brute force solutions that you can come up with optimizations."
        },
        {
            "id": 55,
            "hint": "So, if we fix one of the numbers, say x, we have to scan the entire array to find the next number y which is value - x where value is the input parameter. Can we change our array somehow so that this search becomes faster?"
        },
        {
            "id": 56,
            "hint": "The second train of thought is, without changing the array, can we use additional space somehow? Like maybe a hash map to speed up the search?"
        }
    ],
    "examples": [
        {
            "id": 77,
            "input": "[2,7,11,15]\n9",
            "output": "[0,1]",
            "explanation": "Because nums[0] + nums[1] == 9, we return [0, 1]."
        },
        {
            "id": 78,
            "input": "[3,2,4]\n6",
            "output": "[1,2]",
            "explanation": "Because nums[1] + nums[2] == 6, we return [1, 2]."
        },
        {
            "id": 79,
            "input": "[3,3]\n6",
            "output": "[0,1]",
            "explanation": "Because nums[0] + nums[1] == 6, we return [0, 1]."
        }
    ]
}