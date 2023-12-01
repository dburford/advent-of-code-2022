package aoc.util.graphs

// Floyd Warshall algo - all pairs shortest path
// https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
//
// let dist be a |V| × |V| array of minimum distances initialized to ∞ (infinity)
// for each edge (u, v) do
//   dist[u][v] ← w(u, v)  // The weight of the edge (u, v)
//
// for each vertex v do
//   dist[v][v] ← 0
//
//
fun allPairs(adjMatrix : Array<IntArray>): Array<IntArray> {


  val dist = adjMatrix.clone()

  for (k in dist.indices)
    for (i in dist.indices)
      for (j in dist.indices)
        if (dist[i][j] > dist[i][k] + dist[k][j])
          dist[i][j] = dist[i][k] + dist[k][j]

  return dist
}


