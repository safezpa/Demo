package offer;

import java.util.*;

//定义树的结点
class TreeNode{
    private Integer id;
    private Integer parentId;
    private int cost;
    private Map<Integer,TreeNode> childs = new HashMap<Integer,TreeNode>();//存放所有子结点 <子结点Id，子结点>
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getParentId() {
        return parentId;
    }
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
    public Map<Integer, TreeNode> getChilds() {
        return childs;
    }
    public void setChilds(Map<Integer, TreeNode> childs) {
        this.childs = childs;
    }
}


public class Main{


    public static void main(String[] args) {

        ArrayList<Integer> _ids = new ArrayList<Integer>();
        ArrayList<Integer> _parents = new ArrayList<Integer>();
        ArrayList<Integer> _costs = new ArrayList<Integer>();

        Scanner in = new Scanner(System.in);
        String line = in.nextLine();

        while(line != null && !line.isEmpty()) {
            if(line.trim().equals("0")) break;
            String []values = line.trim().split(" ");
            if(values.length != 3) {
                break;
            }
            _ids.add(Integer.parseInt(values[0]));
            _parents.add(Integer.parseInt(values[1]));
            _costs.add(Integer.parseInt(values[2]));
            line = in.nextLine();
        }
        int res = resolve(_ids, _parents, _costs);

        System.out.println(String.valueOf(res));
    }

    // write your code here
    public static int resolve(ArrayList<Integer> ids, ArrayList<Integer> parents, ArrayList<Integer> costs) {
        TreeNode root = creatTree(ids, parents, costs);
        IteratorTree(root, 0);

        //获取ArrayList中最大值
        int max = pathValueList.get(0);
        for(int i = 1; i < pathValueList.size(); i++){
            if(pathValueList.get(i) > max){
                max = pathValueList.get(i);
            }
        }
        return max;
    }

    //构建一棵多叉树
    public static TreeNode creatTree(ArrayList<Integer> ids, ArrayList<Integer> parents, ArrayList<Integer> costs){
        //创建根节点
        TreeNode root = new TreeNode();
        Map<Integer, TreeNode> maps = new HashMap<Integer, TreeNode>();
        //遍历所有结点信息，将所有结点相关信息放入maps中
        for(int i = 0; i < ids.size(); i++){
            TreeNode node = new TreeNode();
            node.setId(ids.get(i));
            node.setParentId(parents.get(i));
            node.setCost(costs.get(i));
            maps.put(ids.get(i), node);
        }
        //遍历map，将父结点为0的结点放到根结点下，否则在相应父节点下添加子结点
        for(Map.Entry<Integer, TreeNode> entry : maps.entrySet()){
            TreeNode node = entry.getValue();
            Integer partentId = node.getParentId();
            if(partentId == 0){
                root.getChilds().put(node.getId(), node);//node.getId()等价于node.getKey()
            }else{
                TreeNode pNode = maps.get(partentId);
                pNode.getChilds().put(node.getId(), node);
            }
        }
        return root;
    }

    //遍历多叉树，寻找最优解
    private static List<Integer> pathValueList = new ArrayList<Integer>();
    public static void IteratorTree(TreeNode root, int sum){

        int tmp = sum;
        if(root != null){
            for(Map.Entry<Integer, TreeNode> entry : root.getChilds().entrySet()){

                sum = tmp;
                sum += entry.getValue().getCost();

                if(entry.getValue().getChilds() != null && entry.getValue().getChilds().size() > 0){
                    IteratorTree(entry.getValue(), sum);
                }else{
                    pathValueList.add(sum);//遍历完一条路径，将这条路径和记录下来
                }
            }
        }
    }

}
