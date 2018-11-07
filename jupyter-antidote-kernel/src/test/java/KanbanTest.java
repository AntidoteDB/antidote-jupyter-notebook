import eu.antidote.jupyter.kanban.common.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KanbanTest extends AbstractAntidoteTest {

    @Test
    public void helloTest() {
        assertEquals("Hello, World!", antidoteService.kanban.hello());
    }
//
//    List<BoardId> listboards() {
//        return currentAntidote.kanban.listboards()
//    }
//
    @Test
    public void createboardTest() {
        BoardId bid = antidoteService.kanban.createboard("b");
        assert(antidoteService.kanban.listboards().contains(bid));
    }
//
//    String renameboard(BoardId bid, String name) {
//        currentAntidote.kanban.renameboard(bid, name)
//        return "Renamed board"
//    }
//
//    BoardMap getboard(BoardId bid) {
//        return currentAntidote.kanban.getboard(bid)
//    }


    @Test
    public void addcolumnTest() {
        BoardId bid = antidoteService.kanban.createboard("b");
        String cname = "c";
        ColumnId cid = antidoteService.kanban.addcolumn(bid, cname);
        assert(antidoteService.kanban.getboard(bid).columnids.contains(cid));
    }

    @Test
    public void deletecolumn() {
        BoardId bid = antidoteService.kanban.createboard("b");
        String cname = "c";
        ColumnId cid = antidoteService.kanban.addcolumn(bid, cname);
        assert(antidoteService.kanban.getboard(bid).columnids.contains(cid));
    }

    @Test
    public void createtaskTest() {
        BoardId bid = antidoteService.kanban.createboard("b");
        String cname = "c";
        ColumnId cid = antidoteService.kanban.addcolumn(bid, cname);
        String tname = "t";
        TaskId tid = antidoteService.kanban.createtask(tname, cid);
        assert(antidoteService.kanban.getcolumn(cid).taskids.contains(tid));
    }

    @Test
    public void lwwTest() {
        BoardId bid = antidoteService.kanban_lww.createboard("y");
        BoardId bid2 = antidoteService.kanban_lww.createboard("x");
        System.out.println(antidoteService.kanban_lww.getboard(bid));
        System.out.println(antidoteService.kanban_lww.getboard(bid2));
        System.out.println(antidoteService.kanban_lww.listboards());


        String cname = "c";
        ColumnId cid = antidoteService.kanban_lww.addcolumn(bid, cname);
        System.out.println(antidoteService.kanban_lww.getcolumn(cid).tasks);

        //System.out.println(antidoteService.kanban_lww.getboard(bid));
        //System.out.println(antidoteService.kanban_lww.getboard(bid2));

        String tname = "t";
        antidoteService.kanban_lww.createtask(tname, cid);
        String tname2 = "t2";
        antidoteService.kanban_lww.createtask(tname2, cid);
        System.out.println("tasks" + antidoteService.kanban_lww.getcolumn(cid).tasks);

        assert(antidoteService.kanban_lww.getcolumn(cid).tasks.contains(tname));
        assert(antidoteService.kanban_lww.getcolumn(cid).tasks.contains(tname2));
        System.out.println(antidoteService.kanban_lww.getcolumn(cid).tasks);
    }



    @Test
    public void deletetaskTest() {
        BoardId bid = antidoteService.kanban.createboard("b");
        String cname = "c";
        ColumnId cid = antidoteService.kanban.addcolumn(bid, cname);
        String tname = "t";
        TaskId tid = antidoteService.kanban.createtask(tname, cid);
        antidoteService.kanban.deletetask(tid);
        assert(!antidoteService.kanban.getcolumn(cid).taskids.contains(tid));
    }


    @Test
    public void movetaskTest() {
        BoardId bid = antidoteService.kanban.createboard("b");

        String cname = "x";
        ColumnId cid = antidoteService.kanban.addcolumn(bid, cname);
        String cname2 = "y";
        ColumnId cid2 = antidoteService.kanban.addcolumn(bid, cname2);

        String tname = "t";
        TaskId tid = antidoteService.kanban.createtask(tname, cid);
        System.out.println(antidoteService.kanban.getcolumn(cid).taskids);
        System.out.println(antidoteService.kanban.getcolumn(cid2).taskids);

        antidoteService.kanban.movetask(tid, cid2);
        System.out.println(antidoteService.kanban.getboard(bid));
        System.out.println(antidoteService.kanban.getcolumn(cid).taskids);
        System.out.println(antidoteService.kanban.getcolumn(cid2).taskids);

        assert(!antidoteService.kanban.getcolumn(cid).taskids.contains(tid));
        assert(antidoteService.kanban.getcolumn(cid2).taskids.contains(tid));
    }
}
