package main

import (
	"fmt"
	"math"
)

func TestDP() {
	// dp := make(map[int]int)
	// fmt.Println(fibonacci(dp, 6))

	// fmt.Println(stairsJump(dp, 3))
	dp := make(map[Pair]bool)
	arr := []int{1, 2, 3, 4}
	fmt.Println(subsetSum(0, 4, arr, 0, dp))
}

func fibonacci(dpFib map[int]int, n int) int {
	if n <= 1 {
		return n
	}

	if val, ok := dpFib[n]; ok {
		return val
	}

	dpFib[n] = fibonacci(dpFib, n-1) + fibonacci(dpFib, n-2)
	return dpFib[n]
}

func stairsJump(dp map[int]int, n int) int {
	if n <= 2 {
		return n
	}

	if val, ok := dp[n]; ok {
		return val
	}

	dp[n] = stairsJump(dp, n-1) + stairsJump(dp, n-2)
	return dp[n]
}

func frogJump(arr []int, dp map[int]int, index int) int {
	n := len(arr)
	if index == n-1 {
		return 0
	}
	if index > n-1 {
		return 99999 // A very large number to simulate infinity
	}
	if val, ok := dp[index]; ok {
		return val
	}

	l := frogJump(arr, dp, index+1) + int(math.Abs(float64(arr[index]-arr[index+1])))

	r := 99999
	if index+2 < n {
		r = frogJump(arr, dp, index+2) + int(math.Abs(float64(arr[index]-arr[index+2])))
	}

	ans := int(math.Min(float64(l), float64(r)))
	dp[index] = ans
	return ans
}

func ninjaTrainingBU(daysTasks [][3]int, day int, last int) int {
	if day == len(daysTasks)-1 {
		max := 0
		for task := 0; task < 3; task++ {
			if task != last {
				max = Max(max, daysTasks[day][task])
			}
		}
		return max
	}

	max := 0

	for task := 0; task < 3; task++ {
		val := daysTasks[0][task] + ninjaTrainingBU(daysTasks, day+1, task)
		max = Max(max, val)
	}

	return max
}

func ninjaTrainingTD(daysTasks [][3]int, day int, last int) int {
	if day == 0 {
		maxPoints := 0
		for task := 0; task < 3; task++ {
			if task != last {
				maxPoints = Max(maxPoints, daysTasks[0][task])
			}
		}
		return maxPoints
	}

	maxPoints := 0

	for task := 0; task < 3; task++ {
		if task != last {
			points := daysTasks[day][task] + ninjaTrainingTD(daysTasks, day-1, task)
			maxPoints = Max(maxPoints, points)
		}
	}

	return maxPoints
}

func Max(a, b int) int {
	if a > b {
		return a
	}
	return b
}

func subsetSum(sum int, k int, arr []int, index int, dp map[Pair]bool) bool {
	if sum == k {
		return true
	}

	if sum > k || index >= len(arr) {
		return false
	}

	if val, ok := dp[Pair{index, sum}]; ok {
		return val
	}

	dp[Pair{index, sum}] = subsetSum(sum+arr[index], k, arr, index+1, dp) || subsetSum(sum, k, arr, index+1, dp)
	return dp[Pair{index, sum}]
}

func partitionSubset(arr []int) bool {
	sum := 0
	for i := 0; i < len(arr); i++ {
		sum += arr[i]
	}
	if sum%2 != 0 {
		return false
	}

	dp := make(map[Pair]bool)

	return subsetSum(0, sum/2, arr, 0, dp)
}
