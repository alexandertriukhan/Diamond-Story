package gridTypes

// Pay attention! This is columns, not rows!

        fun square(): Array<IntArray> {                               // 8x8
            val col1: IntArray = intArrayOf(1, 1, 1, 1, 1, 1, 1, 1)   //    [][][][][][][][]
            val col2: IntArray = intArrayOf(1, 1, 1, 1, 1, 1, 1, 1)   //    [][][][][][][][]
            val col3: IntArray = intArrayOf(1, 1, 1, 1, 1, 1, 1, 1)   //    [][][][][][][][]
            val col4: IntArray = intArrayOf(1, 1, 1, 0, 0, 1, 1, 1)   //    [][][][][][][][]
            val col5: IntArray = intArrayOf(1, 1, 1, 0, 0, 1, 1, 1)   //    [][][][][][][][]
            val col6: IntArray = intArrayOf(1, 1, 1, 1, 1, 1, 1, 1)   //    [][][][][][][][]
            val col7: IntArray = intArrayOf(1, 1, 1, 1, 1, 1, 1, 1)   //    [][][][][][][][]
            val col8: IntArray = intArrayOf(1, 1, 1, 1, 1, 1, 1, 1)   //    [][][][][][][][]
            return arrayOf(col1, col2, col3, col4,
                    col5, col6, col7, col8)
        }

        fun circle(): Array<IntArray> {                               // 8x8
            val col1: IntArray = intArrayOf(0, 0, 0, 1, 1, 0, 0, 0)   //          [][]
            val col2: IntArray = intArrayOf(0, 0, 1, 1, 1, 1, 0, 0)   //        [][][][]
            val col3: IntArray = intArrayOf(0, 1, 1, 1, 1, 1, 1, 0)   //      [][][][][][]
            val col4: IntArray = intArrayOf(1, 1, 1, 1, 1, 1, 1, 1)   //    [][][][][][][][]
            val col5: IntArray = intArrayOf(1, 1, 1, 1, 1, 1, 1, 1)   //    [][][][][][][][]
            val col6: IntArray = intArrayOf(0, 1, 1, 1, 1, 1, 1, 0)   //      [][][][][][]
            val col7: IntArray = intArrayOf(0, 0, 1, 1, 1, 1, 0, 0)   //        [][][][]
            val col8: IntArray = intArrayOf(0, 0, 0, 1, 1, 0, 0, 0)   //          [][]
            return arrayOf(col1, col2, col3, col4,
                    col5, col6, col7, col8)
        }

        fun puzzle() : Array<IntArray> {
            val col1: IntArray = intArrayOf(0, 1, 0, 1, 0, 1, 0, 1, 0)
            val col2: IntArray = intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1)
            val col3: IntArray = intArrayOf(0, 1, 1, 1, 1, 1, 1, 1, 0)
            val col4: IntArray = intArrayOf(1, 1, 1, 1, 0, 1, 1, 1, 1)
            val col5: IntArray = intArrayOf(1, 1, 1, 1, 0, 1, 1, 1, 1)
            val col6: IntArray = intArrayOf(0, 1, 1, 1, 1, 1, 1, 1, 0)
            val col7: IntArray = intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1)
            val col8: IntArray = intArrayOf(0, 1, 0, 1, 0, 1, 0, 1, 0)
            return arrayOf(col1, col2, col3, col4,
                    col5, col6, col7, col8)
        }

        fun lToRWaterfall() : Array<IntArray> {                       // 8x8
            val col1: IntArray = intArrayOf(0, 0, 0, 0, 0, 1, 1, 1)   //    [][][]
            val col2: IntArray = intArrayOf(0, 0, 0, 0, 1, 1, 1, 1)   //    [][][][]
            val col3: IntArray = intArrayOf(0, 0, 0, 1, 1, 1, 1, 1)   //      [][][][]
            val col4: IntArray = intArrayOf(0, 0, 1, 1, 1, 1, 1, 0)   //        [][][][]
            val col5: IntArray = intArrayOf(0, 1, 1, 1, 1, 1, 0, 0)   //          [][][][]
            val col6: IntArray = intArrayOf(1, 1, 1, 1, 1, 0, 0, 0)   //            [][][][]
            val col7: IntArray = intArrayOf(1, 1, 1, 1, 0, 0, 0, 0)   //              [][][][]
            val col8: IntArray = intArrayOf(1, 1, 1, 0, 0, 0, 0, 0)   //                [][][]
            return arrayOf(col1, col2, col3, col4,
                    col5, col6, col7, col8)
        }