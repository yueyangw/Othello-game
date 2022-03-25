package Othello;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MCTS {
    final int INF = 0x3f3f3f3f;
    int n;
    int timeLimit;
    int maxIterations;
    Random rand;

    MCTS(int timeLimit, int n, int tot, int seed) {
        this.timeLimit = timeLimit;
        this.n = n;
        this.maxIterations = tot;
        rand = new Random(seed);
        Node.seed = seed;
    }

    public int[] nextStep(int[][] board, int color) {
        Step res = mcts(board, color);
        int[] ans = {res.y, res.x};
        System.out.printf("MCTS : %d:(%d,%d)\n", color, ans[0], ans[1]);
        return ans;
    }

    Node getBestChild(Node now) {
        if(!now.isFullyExpanded()) return null;
        double maxScore = -1e9;
        Node res = null;
        int studentsCnt = now.childrensCnt();
        for(int i = 0; i < studentsCnt; i++) {
            Node child = now.getChild(i);
            double first = (double)child.getValue() / child.getVisitCnt();
            double second = Math.sqrt(Math.log(now.getVisitCnt() + 1) / (child.getVisitCnt()));
            double score = first + second;
            if(score > maxScore) {
                maxScore = score;
                res = child;
            }
        }
        return res;
    }

    Node getWiner(Node root) {
        double maxx = -1;
        Node bestChild = null;
        int cnt = root.childrensCnt();
        for(int i = 0; i < cnt; i++) {
            Node child = root.getChild(i);
            double value = child.getValue() / (double)child.getVisitCnt();
            if(value > maxx) {
                maxx = value;
                bestChild = child;
            }
        }
        return bestChild;
    }

    // minimax搜索
    Step mcts(int[][] board, int color) {
        Node root = new Node(color, board);
        Node choice = null;
        long beginTime = System.currentTimeMillis() / 1000;
        int iterationCnt = 1;
        while(true) {
            Node temp = root;
            while(!temp.isTerminated() && temp.isFullyExpanded()) {
                temp = getBestChild(temp);
            }
            while(!temp.isFullyExpanded() && !temp.isTerminated()) {
                temp = temp.expand();
            }
            int[][] state = temp.getState().clone();
            if(!temp.isTerminated()) {
                int nxtColor = color;
                while(true) {
                    ArrayList<Step> nxtStep = legalPosition(state, nxtColor);
                    if(nxtStep.size() == 0) {
                        nxtStep = legalPosition(state, -nxtColor);
                        if(nxtStep.size() == 0) {
                            break;
                        }
                        nxtColor = -nxtColor;
                        continue;
                    }
                    int idx = rand.nextInt(nxtStep.size());
                    state = nextBoard(state, nxtStep.get(idx).x, nxtStep.get(idx).y, nxtColor);
                    nxtColor = -nxtColor;
                }
            }
            double val = evaluate(state, color);
            while(temp != null) {
                temp.update(val);
                temp = temp.getParent();
            }
            choice = getWiner(root);
            long nowTime = System.currentTimeMillis() / 1000;
            if(nowTime - beginTime > timeLimit || iterationCnt > maxIterations) {
                System.out.println(nowTime);
                System.out.println(beginTime);
                System.out.println(maxIterations);
                System.out.println(timeLimit);
                break;
            }
            iterationCnt++;
        }
        System.out.printf("Winning rate : %f%%\n", 100 * root.getValue() / (double)root.getVisitCnt());
        System.out.printf("Iteration : %d\n", iterationCnt);
        Step res = choice.operation;
        return res;
    }

    // 统计01矩阵中1的个数
    int evaluate(int[][] board, int color) {
        int cnt = 0;
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= n; j++) {
                if(board[i][j] == color) cnt++;
            }
        }
        if(cnt > 32) return 1;
        else return 0;
    }

//    // 矩阵转置
//    int[][] trans(int[][] board) {
//        int N = board[0].length;
//        int[][] newBoard = new int[N][N];
//        for(int i = 1; i <= n; i++) {
//            for(int j = 1; j <= n; j++) {
//                newBoard[i][j] = board[j][i];
//            }
//        }
//        return newBoard;
//    }

//    int getStable(int[][] board, int color) {
//        int res = 0;
//        int[] fx = {0, 1, 0, -1};
//        int[] fy = {1, 0, -1, 0};
//        for(int i = 1; i <= n; i++) {
//            for(int j = 1; j <= n; j++) {
//                if(board[i][j] != 0) {
//                    boolean flag = true;
//                    for(int k = 0; k < 4; k++) {
//                        int x = i;
//                        int y = j;
//                        while(x > 0 && x <= 8 && y > 0 && y <= 8) {
//                            if(board[x][y] == 0) {
//                                flag = false;
//                                break;
//                            }
//                            x += fx[k];
//                            y += fy[k];
//                        }
//                        if(flag) res++;
//                    }
//                }
//            }
//        }
//        fx = new int[]{1, 1, 8, 8};
//        fy = new int[]{1, 8, 1, 8};
//        for(int k = 0; k < 4; k++) {
//            if(board[fx[k]][fy[k]] == color) res++;
//        }
//        return res;
//    }

    // 估值函数
//    int evaluate(int[][] board, int moves, int color) {
//        int nextColor = -color;
//        int powerRes = 0;
//        for(int i = 1; i <= n; i++) {
//            for(int j = 1; j <= n; j++) {
//                if(board[i][j] == color) {
//                    powerRes += valueMap[i-1][j-1];
//                }
//                else if(board[i][j] == nextColor){
//                    powerRes -= valueMap[i-1][j-1];
//                }
//            }
//        }
//        int moves_ = moves - count(legalPosition(board, -color));
//        int stable = getStable(board, color);
//        return powerRes + 15 * moves_ + 10 * stable;
//    }

    // 落子
    public static int[][] nextBoard(int[][] board, int x, int y, int type) {
        int n = 1;
        int color = type;
        int state[][] = new int[10][10];
        for(int i = 0; i <= 9; i++){
            state[0][i] = 2;
            state[i][0] = 2;
            state[9][i] = 2;
            state[i][9] = 2;
        }
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                state[i][j] = board[i][j];
            }
        }
        int fx[] = {-1, 1, 0, 0, -1, -1, 1, 1};
        int fy[] = {0, 0, -1, 1, -1, 1, -1, 1};
        while(n-- > 0){
            boolean flag = false;
            for(int i = 0; i <= 7; i++){
                if(state[x+fx[i]][y+fy[i]] != -color) continue;
                int k = 1;
                while(state[x+k*fx[i]][y+k*fy[i]] == -color){
                    k++;
                }
                if(state[x+k*fx[i]][y+k*fy[i]] == color){
                    flag = true;
                    state[x][y] = color;
                    for(int j = 1; j <= k; j++){
                        state[x+j*fx[i]][y+j*fy[i]] = color;
                    }
                }
            }
            if (flag) {
                state[x][y] = color;
            }
        }
        return state;
    }

    // 合法落子位置
    public static ArrayList<Step> legalPosition(int[][] board, int type){
        int [][]a =new int[8+2][8+2];
        int []kong =new int[5+2];
        boolean can=false;
        boolean isthere=false;
        int jimmy=type;
        int alpaca=-jimmy;
        int []x=new int [70];
        int []y=new int [70];
        int st=1;
        int [][]ans=new int[8+2][8+2];
        for(int j=1;j<=8;j++) {
            for (int k = 1; k <= 8; k++) {
                a[j][k] = board[j][k];
                if (a[j][k] == jimmy) {
                    x[st] = j;
                    y[st] = k;
                    st++;
                    isthere = true;
                }
            }
        }
        if(isthere) {
            for(int j=1;j<st;j++) {
                int xx=x[j];
                int yy=y[j];
                while(a[xx][yy]==jimmy)xx--;
                if(xx>=2 && a[xx][yy]==alpaca) {
                    while(a[xx][yy]==alpaca)xx--;
                    if(xx>=1 && a[xx][yy]==0)
                        ans[xx][yy]=1;
                }
                xx=x[j];
                yy=y[j];
                while(a[xx][yy]==jimmy)xx++;
                if(xx<=7 && a[xx][yy]==alpaca) {
                    while(a[xx][yy]==alpaca)xx++;
                    if(xx<=8 && a[xx][yy]==0)
                        ans[xx][yy]=1;
                }
                xx=x[j];
                yy=y[j];
                while(a[xx][yy]==jimmy)yy++;
                if(yy<=7 && a[xx][yy]==alpaca) {
                    while(a[xx][yy]==alpaca)yy++;
                    if(yy<=8 && a[xx][yy]==0)
                        ans[xx][yy]=1;
                }
                xx=x[j];
                yy=y[j];
                while(a[xx][yy]==jimmy)yy--;
                if(yy>=2 && a[xx][yy]==alpaca) {
                    while(a[xx][yy]==alpaca)yy--;
                    if(yy>=1 && a[xx][yy]==0)
                        ans[xx][yy]=1;
                }
                xx=x[j];
                yy=y[j];
                while(a[xx][yy]==jimmy) {
                    xx--;
                    yy--;
                }
                if(yy>=2 && xx>=2 && a[xx][yy]==alpaca) {
                    while(a[xx][yy]==alpaca){
                        yy--;
                        xx--;
                    }
                    if(yy>=1 && xx>=1 && a[xx][yy]==0)
                        ans[xx][yy]=1;
                }
                xx=x[j];
                yy=y[j];
                while(a[xx][yy]==jimmy) {
                    xx++;
                    yy++;
                }
                if(yy<=7 && xx<=7 && a[xx][yy]==alpaca) {
                    while(a[xx][yy]==alpaca) {
                        yy++;
                        xx++;
                    }
                    if(yy<=8 && xx<=8 && a[xx][yy]==0) {
                        ans[xx][yy] = 1;
                    }
                }
                xx=x[j];
                yy=y[j];
                while(a[xx][yy]==jimmy) {
                    xx--;
                    yy++;
                }
                if(yy<=7 && xx>=2 && a[xx][yy]==alpaca) {
                    while(a[xx][yy]==alpaca) {
                        yy++;
                        xx--;
                    }
                    if(yy<=8 && xx>=1 && a[xx][yy]==0) {
                        ans[xx][yy] = 1;
                    }
                }
                xx=x[j];
                yy=y[j];
                while(a[xx][yy]==jimmy) {
                    xx++;
                    yy--;
                }
                if(yy>=2 && xx<=7 && a[xx][yy]==alpaca) {
                    while(a[xx][yy]==alpaca) {
                        yy--;
                        xx++;
                    }
                    if(yy>=1 && xx<=8 && a[xx][yy]==0) {
                        ans[xx][yy] = 1;
                    }
                }
                xx=x[j];
                yy=y[j];
            }
        }
        ArrayList<Step> legalList= new ArrayList<Step>();
        for(int h=1;h<=8;h++) {
            for (int j = 1; j <= 8; j++) {
                if (ans[h][j] != 0) {
                    legalList.add(new Step(h, j, 0));
                }
            }
        }
        return legalList;
    }
}

class Node {
    private int visitCnt;
    private double value;
    private int color;
    private Node parent;
    private ArrayList<Node> childrensList = new ArrayList<Node>();
    private ArrayList<Step> nxtStep;
    private int[][] state = new int[10][10];
    private Random rand;

    private void clear() {
        this.state = new int[10][10];
        this.childrensList = new ArrayList<Node>();
    }

    public Step operation;
    public static int seed;

    Node(int type, int[][] board, Node parent, Step op) {
        this.color = type;
        this.state = board.clone();
        this.parent = parent;
        this.nxtStep = MCTS.legalPosition(board, color);
        this.rand = new Random(seed);
        operation = op;
    }
    Node(int type, int[][] board) {
        this.color = type;
        this.state = board.clone();
        this.parent = null;
        this.rand = new Random(seed);
        this.nxtStep = MCTS.legalPosition(board, color);
    }

    int[][] getState() {
        return state;
    }

    boolean isTerminated() {
        return nxtStep.size() == 0;
    }

    boolean isFullyExpanded() {
        return childrensCnt() == nxtStep.size();
    }

    int childrensCnt() {
        return childrensList.size();
    }

    int getVisitCnt() {
        return visitCnt;
    }

    double getValue() {
        return value;
    }

    Node getChild(int idx) {
        return this.childrensList.get(idx);
    }

    Node getParent() {
        return this.parent;
    }

    Node expand() {
        if(isFullyExpanded()) return null;

        ArrayList<Step> nxtStep = MCTS.legalPosition(state, color);
        int idx = rand.nextInt(nxtStep.size() - childrensCnt());
        //System.out.printf("idx:%d\nsizeof(nxtStep):%d\nchildren:%d\n", idx, nxtStep.size(), childrensList.size());
        int[][] board = MCTS.nextBoard(state, nxtStep.get(idx).x, nxtStep.get(idx).y, color);
        Node child = new Node(-color, board, this, nxtStep.get(idx));
        Collections.swap(nxtStep, idx, nxtStep.size() - childrensCnt() - 1);
        childrensList.add(child);
        return child;
    }

    void pushBack(double val) {
        Node temp = this;
        while(temp != null) {
            temp.visitCnt++;
            temp.value += val;
            temp = temp.parent;
        }
    }

    void update(double val) {
        value += val;
        visitCnt++;
    }
}