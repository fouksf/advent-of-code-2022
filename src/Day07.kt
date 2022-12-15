import java.io.File
import java.util.Deque

fun main() {
    class Dir(val name: String, val parent: Dir?) {
        val files = HashMap<String, Long>()
        val children = ArrayList<Dir>()

        fun getSize(): Long {
            return children.sumOf { it.getSize() } + files.values.sum()
        }
    }

    fun addDirContent(dir: Dir, iterator: ListIterator<String>) {
        var content = ""
        while (iterator.hasNext()) {
            content = iterator.next()
            if (content.startsWith("$")) {
                break
            }
            if (content.startsWith("dir")) {
                val child = Dir(content.split(" ")[1], dir)
                dir.children.add(child)
            } else{
                dir.files[content.split(" ")[1]] = content.split(" ")[0].toLong()
            }
        }
        if (iterator.hasNext()) {
            iterator.previous()
        }
    }

    fun parseDirs(input: List<String>): Dir {
        val base = Dir("/", null)
        var currentDir = base
        val lineIterator = input.listIterator()
        var line = lineIterator.next()
        while (lineIterator.hasNext()) {
            line = lineIterator.next()
            if (line.startsWith("$ ls")) {
                addDirContent(currentDir, lineIterator)
            } else if (line.startsWith("$ cd")) {
                currentDir = if (line.split(" ")[2] == "..") {
                    currentDir.parent!!
                } else {
                    currentDir.children.find { it.name == line.split(" ")[2] }!!
                }
            } else {
                throw Exception("Hi There!")
            }
        }
        return base
    }

    fun getAllDirs(base: Dir): List<Dir> {
        val allDirs = mutableListOf(base)
        if (base.children.isNotEmpty()) {
            allDirs.addAll(base.children.map { getAllDirs(it) }.flatten())
        }
        return allDirs
    }

    fun part1(base: Dir): Long {
        return getAllDirs(base).map { it.getSize() }.filter { it <= 100000 }.sum()
    }

    fun part2(base: Dir): Long {
        val totalSpace = 70000000
        val neededSpace = 30000000
        val availableSpace = totalSpace - base.getSize()
        val minToDeleteSpace = neededSpace - availableSpace
        return getAllDirs(base).map { it.getSize() }.filter { it >= minToDeleteSpace }.min()
    }

// test if implementation meets criteria from the description, like:
    val dirs = parseDirs(readInput("Day07_test"))
//    println(part1(dirs))
    println(part2(dirs))

    val input = parseDirs(readInput("Day07"))
//    println(part1(input))
    println(part2(input))
}
