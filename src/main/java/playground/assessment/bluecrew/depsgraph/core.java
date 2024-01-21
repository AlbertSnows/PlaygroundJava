package playground.assessment.bluecrew.depsgraph;
/*
Implement a Workspace package Builder.
We have a workspace that contains N amount of packages.
Each package can have M dependencies.

If the workspace is buildable we output "Success" in case of failure we output a failure message with the reason of failure.
e.g
Workspace contains packages:
A - Dependencies -> B,C
B - No dependency
C - Dependency -> D
D - No dependency

A function is provided to us globally which is `Compile` we need to call the compile function for each of the packages in the workspace.
This function won't be compiling sub-dependencies of a package and therefore would have to be compiled before. */

// workspace, one to many (N) packages, one to many (M) deps for package
// buildable -> bool
// success -> sucess, failure -> Error(why?)
// fn Compile,

// goal: implement a compiling function for a workspace that compiles the package dependencies in the correct order
// success -> dep graph is a tree
// failure -> dep graph has a cycle
//

// HashSet<Node> visited;
// A -> [B, C] -> B -> no deps (compile) -> go back up -> C -> no deps (compile) -> go back up
// -> no deps left for a -> go back up -> A has no more deps that aren't compiled - > compile A

import java.util.List;

//        a -> b, d
//        b - c
//        d - c
//        c - nothing
//        function CheckAndCompileLevel(Collection node, HashSet<Packages> visited, HashMap<name, Package> &compiled) -> Result<CompiledTree, CycleError> {
//                var dependencies = node.dependencies;
//        if(dependencies.isEmpty()) {
//            return new CompiledTree(Compile(node));
//        }
//        var children = new ArrayList<CompileTree>();
//        for(var dependency : dependencies) {
//            var cycle = visited.contains(dependency);
//            if(cycle) {
//                return Result::Error("Error! Cycle at ", [dependency.name, node.name]);
//            }
//            if(!compiled.contains(dependency.name)) {
//                visited.add(dependency);
//                var treeForDep = CheckAndCompileLevel(dependency, visited);
//                visited.remove(dependency);
//                children.add(treeForDep);
//                compiled.add(dependency.name, treeForDep);
//            }
//        }
//        var compiledNode = Compile(node);
//        var result = new CompiledTree(node, children);
//        return result;
//}
//
//        function CompileOrFaileDepsTree(Collection suspiciousTree) -> Result<CompiledTree, CycleError> {
//                // var processingQueue = new Deque();
//                var visited = new HashSet<>();
//        var compiled = new HashMap<name, CompiledTree>();
//        // var result = new ArrayList<CompileTree>();
//        for(var package : suspiciousTree) { // package { name, deps }
//            // visited.add(package);
//            var result = CheckAndCompileLevel(package, new HashSet<>(), compiled);
//            if(result.isError()) {
//                return result.getError();
//            }
//            compiled.add(package.name, safeResult);
//            // visited.remove(package);
//        }
//        return compiled;
//}
public class core {
    public static void main(String[] args) {

        var package_list = List.of(
        );
    }
}
