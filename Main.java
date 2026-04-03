import java.util.*;

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        Main main = new Main();
        System.out.println(main.maxProfit(new int[] { 1, 7, 6, 4, 3 }));

    }

    public int taskScheduler(List<Character> tasks, int n) {
        int time = 0;
        Map<Character, Integer> hm = new HashMap<>();
        for (char ch : tasks) {
            hm.put(ch, hm.getOrDefault(ch, 0) + 1);
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        maxHeap.addAll(hm.values());

        while (!maxHeap.isEmpty()) {
            int cycle = n + 1;
            List<Integer> temp = new ArrayList<>();

            for (int i = 0; i < cycle; i++) {
                if (!maxHeap.isEmpty())
                    temp.add(maxHeap.poll());
            }

            for (int count : temp) {
                if (count - 1 > 0) {
                    maxHeap.add(count - 1);
                }
            }

            time += maxHeap.isEmpty() ? temp.size() : cycle;
        }

        return time;
    }

    public ListNode mergeLists(ListNode[] lists) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        PriorityQueue<ListNode> heap = new PriorityQueue<>((a, b) -> a.val - b.val);

        for (ListNode node : lists) {
            if (node != null)
                heap.add(node);
        }

        while (!heap.isEmpty()) {
            ListNode small = heap.poll();
            curr.next = small;
            curr = curr.next;

            if (small.next != null) {
                heap.add(small.next);
            }
        }

        return dummy.next;
    }

    public void ranking(int[] arr) {
        int rank = 0;
        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> a - b);
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            heap.add(arr[i]);
        }

        while (!heap.isEmpty()) {
            if (!map.containsKey(heap.peek())) {
                map.put(heap.poll(), ++rank);
            } else
                heap.poll();
        }

        System.out.println(map);
    }

    public boolean handOfStraights(int[] arr, int size) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i : arr) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }

        while (!map.isEmpty()) {
            int first = map.firstKey();
            for (int i = 0; i < size; i++) {
                if (map.containsKey(first + i)) {
                    int count = map.get(first + i);
                    if (count == 1) {
                        map.remove(first + i);
                    } else
                        map.put(first + i, count - 1);
                } else
                    return false;
            }
        }

        return true;
    }

    public int minInsertions(String s, int i, int j, int dp[][]) {
        if (i == j || i > j)
            return 0;

        if (dp[i][j] != -1)
            return dp[i][j];

        if (s.charAt(i - 1) == s.charAt(j - 1))
            dp[i][j] = minInsertions(s, i + 1, j - 1, dp);
        else
            dp[i][j] = Math.min(1 + minInsertions(s, i + 1, j, dp), 1 + minInsertions(s, i, j - 1, dp));

        return dp[i][j];
    }

    public int minInsertionsTab(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];

        // substrings of length 1 are already palindromes
        for (int i = 0; i < n; i++) {
            dp[i][i] = 0;
        }

        // fill by increasing substring length
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;

                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1];
                } else {
                    dp[i][j] = Math.min(1 + dp[i + 1][j], 1 + dp[i][j - 1]);
                }
            }
        }

        return dp[0][n - 1]; // whole string
    }

    public int minDistance(String w1, String w2, int i, int j, int dp[][]) {
        if (i < 0)
            return j + 1;
        if (j < 0)
            return i + 1;

        if (dp[i][j] != -1)
            return dp[i][j];

        if (w1.charAt(i) == w2.charAt(j))
            dp[i][j] = minDistance(w1, w2, i - 1, j - 1, dp);
        else
            dp[i][j] = Math.min(1 + minDistance(w1, w2, i - 1, j - 1, dp),
                    Math.min(1 + minDistance(w1, w2, i - 1, j, dp), 1 + minDistance(w1, w2, i, j - 1, dp)));

        return dp[i][j];
    }

    public int maxProfit(int[] prices) {
        int profit = Integer.MIN_VALUE;
        int cost = Integer.MAX_VALUE;

        for (int i = 0; i < prices.length; i++) {
            cost = Math.min(cost, prices[i]);
            profit = Math.max(profit, prices[i] - cost);
        }

        return profit;
    }

    public int maxProfitTab(int[] prices) {
        int[][] dp = new int[prices.length + 1][2];
        dp[prices.length][0] = 0;
        dp[prices.length][1] = 0;

        for (int i = prices.length - 1; i >= 0; i--) {
            for (int j = 0; j < 2; j++) {
                if (j == 1) {
                    dp[i][j] = Math.max(-prices[i] + dp[i + 1][0],
                            dp[i + 1][1]);
                } else {
                    dp[i][j] = Math.max(prices[i] + dp[i + 1][1],
                            dp[i + 1][0]);
                }
            }
        }

        return dp[0][1];

    }

    public int maxProfitForCapTab(int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n + 1][2][3];

        for (int i = 0; i < n; i++) {
            for (int buy = 0; buy < 2; buy++) {
                dp[i][buy][0] = 0;
            }
        }

        for (int buy = 0; buy < 2; buy++) {
            for (int cap = 0; cap < 3; cap++) {
                dp[n][buy][cap] = 0;
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int buy = 0; buy < 2; buy++) {
                for (int cap = 0; cap < 3; cap++) {
                    if (buy == 1) {
                        dp[i][buy][cap] = Math.max(-prices[i] + dp[i + 1][0][cap],
                                dp[i + 1][1][cap]);
                    } else {
                        dp[i][buy][cap] = Math.max(prices[i] + dp[i + 1][1][cap - 1],
                                dp[i + 1][0][cap]);
                    }
                }
            }
        }

        return dp[0][1][2];
    }

    public int maxProfitForCap(int[] prices, int i, int buy, int[][][] dp, int cap) {
        if (i == prices.length)
            return 0;

        if (cap == 0)
            return 0;

        if (dp[i][buy][cap] != -1)
            return dp[i][buy][cap];

        if (buy == 1) {
            dp[i][buy][cap] = Math.max(-prices[i] + maxProfitForCap(prices, i + 1, 0, dp, cap),
                    maxProfitForCap(prices, i + 1, 1, dp, cap));
        } else {
            dp[i][buy][cap] = Math.max(prices[i] + maxProfitForCap(prices, i + 1, 1, dp, cap - 1),
                    maxProfitForCap(prices, i + 1, 0, dp, cap));
        }

        return dp[i][buy][cap];
    }

    public int maxProfitForFee(int[] prices, int i, int buy, int[][] dp, int fee) {
        if (i == prices.length)
            return 0;

        if (dp[i][buy] != -1)
            return dp[i][buy];

        if (buy == 1) {
            dp[i][buy] = Math.max(-prices[i] + maxProfitForFee(prices, i + 1, 0, dp, fee),
                    maxProfitForFee(prices, i + 1, 1, dp, fee));
        } else {
            dp[i][buy] = Math.max(prices[i] + maxProfitForFee(prices, i + 1, 1, dp, fee) - fee,
                    maxProfitForFee(prices, i + 1, 0, dp, fee));
        }

        return dp[i][buy];
    }

    public int maxProfitCool(int[] prices, int i, int buy, boolean cool) {
        if (i == prices.length)
            return 0;

        if (cool)
            return maxProfitCool(prices, i + 1, 1, false);

        if (buy == 1)
            return Math.max(-prices[i] + maxProfitCool(prices, i + 1, 0, false),
                    maxProfitCool(prices, i + 1, 1, false));
        return Math.max(maxProfitCool(prices, i + 1, 1, true) + prices[i], maxProfitCool(prices, i + 1, 0, false));
    }

    public int maxProfit(int[] prices, int i, int buy, int[][] dp) {
        if (i == prices.length)
            return 0;

        if (dp[i][buy] != -1)
            return dp[i][buy];

        if (buy == 1) {
            dp[i][buy] = Math.max(-prices[i] + maxProfit(prices, i + 1, 0, dp),
                    maxProfit(prices, i + 1, 1, dp));
        } else {
            dp[i][buy] = Math.max(prices[i] + maxProfit(prices, i + 1, 1, dp),
                    maxProfit(prices, i + 1, 0, dp));
        }

        return dp[i][buy];
    }

    public int longestIncreasingSubSeq(int[] arr, int curr, int prev) {
        if (curr == arr.length)
            return 0;

        int len = longestIncreasingSubSeq(arr, curr + 1, prev);

        if (prev == -1 || arr[curr] > arr[prev])
            len = Math.max(1 + longestIncreasingSubSeq(arr, curr + 1, curr), len);

        return len;
    }

}