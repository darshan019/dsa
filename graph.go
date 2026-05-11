package main

import (
	"fmt"
	"strconv"
)

// Pair represents a relative coordinate from the origin of an island
type Pair struct {
	first  int
	second int
}

// countDistinctIslands counts unique island shapes in a grid
func countDistinctIslands(grid [][]int) int {
	n := len(grid)
	m := len(grid[0])
	vis := make([][]int, n)
	for i := range vis {
		vis[i] = make([]int, m)
	}

	set := make(map[string]bool)

	for i := 0; i < n; i++ {
		for j := 0; j < m; j++ {
			if vis[i][j] == 0 && grid[i][j] == 1 {
				vec := []Pair{}
				vec = dfsUniqIsland(i, j, grid, vis, vec, i, j)
				shapeKey := serialize(vec)
				set[shapeKey] = true
			}
		}
	}

	return len(set)
}

// dfsUniqIsland does DFS to build relative shape of an island
func dfsUniqIsland(row, col int, grid [][]int, vis [][]int, vec []Pair, r0, c0 int) []Pair {
	vis[row][col] = 1
	n := len(grid)
	m := len(grid[0])
	vec = append(vec, Pair{row - r0, col - c0})

	delrow := [4]int{-1, 0, 1, 0}
	delcol := [4]int{0, -1, 0, 1}

	for i := 0; i < 4; i++ {
		nrow := row + delrow[i]
		ncol := col + delcol[i]

		if nrow >= 0 && nrow < n && ncol >= 0 && ncol < m && vis[nrow][ncol] == 0 && grid[nrow][ncol] == 1 {
			vec = dfsUniqIsland(nrow, ncol, grid, vis, vec, r0, c0)
		}
	}

	return vec
}

// serialize converts []Pair into a string key
func serialize(vec []Pair) string {
	key := ""
	for _, p := range vec {
		key += strconv.Itoa(p.first) + "," + strconv.Itoa(p.second) + ";"
	}
	return key
}

func TestGraph() {
	grid := [][]int{
		{1, 1, 1, 1, 0},
		{1, 1, 0, 0, 0},
		{0, 0, 0, 1, 1},
		{0, 0, 0, 1, 1},
	}

	result := countDistinctIslands(grid)
	fmt.Println("Number of distinct islands:", result)
}

func isBipartiteBFS(V int, list map[int][]int) bool {
	color := make([]int, V)
	for i := 0; i < V; i++ {
		color[i] = -1 // colors array of each node index initialized with -1
	}

	for i := 0; i < V; i++ {
		if color[i] == -1 {
			q := []int{i}
			color[i] = 0

			for len(q) > 0 {
				node := q[0]
				q = q[1:]

				for _, neighbor := range list[node] {
					switch color[neighbor] {
					case -1:
						color[neighbor] = 1 - color[node]
						q = append(q, neighbor)
					case color[node]:
						return false
					}
				}
			}
		}
	}

	return true
}

func isBipartiteDFS(V int, list map[int][]int) bool {
	color := make([]int, V)
	for i := 0; i < V; i++ {
		color[i] = -1 // colors array of each node index initialized with -1
	}

	for i := 0; i < V; i++ {
		if color[i] == -1 {
			if !dfsBipartite(i, 0, list, color) {
				return false
			}
		}
	}

	return true
}

func dfsBipartite(node int, col int, list map[int][]int, color []int) bool {
	color[node] = col

	for _, neighbor := range list[node] {
		switch color[neighbor] {
		case -1:
			if !dfsBipartite(neighbor, 1-col, list, color) {
				return false
			}
		case col:
			return false
		}
	}

	return true
}

func hasCycleUnDir(list map[int][]int) bool {
	vis := make(map[int]bool)
	for i := range list {
		vis[i] = false
	}

	for node := range list {
		if !vis[node] {
			if dfsCycleUnDir(list, vis, node, -1) {
				return true
			}
		}
	}

	return false
}

func dfsCycleUnDir(list map[int][]int, vis map[int]bool, node, parent int) bool {
	vis[node] = true

	for _, neighbour := range list[node] {
		if !vis[neighbour] {
			if dfsCycleUnDir(list, vis, neighbour, node) {
				return true
			}
		} else if neighbour != parent {
			return true
		}
	}

	return false
}

func hasCycleDir(list map[int][]int) bool {
	vis := make(map[int]bool)
	path := make(map[int]bool)

	for node := range list {
		vis[node] = false
		path[node] = false
	}

	for node := range list {
		if !vis[node] {
			if dfsCycleDir(list, node, path, vis) {
				return true
			}
		}
	}

	return false
}

func dfsCycleDir(list map[int][]int, node int, path, vis map[int]bool) bool {
	vis[node] = true
	path[node] = true

	for _, neighbour := range list[node] {
		if !vis[neighbour] {
			if dfsCycleDir(list, neighbour, path, vis) {
				return true
			} else if path[neighbour] {
				return true
			}

		}
	}

	path[node] = false
	return false
}
