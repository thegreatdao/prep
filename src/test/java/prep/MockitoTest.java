package prep;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;


public class MockitoTest
{
	@SuppressWarnings("unchecked")
	@Test
	public void testMock()
	{
		List<String> mockedList = mock(List.class);

		// using mock object
		mockedList.add("one");
		mockedList.clear();

		// verification
		verify(mockedList).add("one");
		verify(mockedList).clear();
	}
	
	@Test(expected=RuntimeException.class)
	@SuppressWarnings("unchecked")
	public void testStubbing()
	{
		//You can mock concrete classes, not only interfaces
		 LinkedList<String> mockedList = mock(LinkedList.class);
		 
		 //stubbing
		 when(mockedList.get(0)).thenReturn("first");
		 when(mockedList.get(1)).thenThrow(new RuntimeException());
		 
		 //following prints "first"
		 System.out.println(mockedList.get(0));
		 
		 //following throws runtime exception
		 System.out.println(mockedList.get(1));
		 
		 //following prints "null" because get(999) was not stubbed
		 System.out.println(mockedList.get(999));
		  
		 //Although it is possible to verify a stubbed invocation, usually it's just redundant
		 //If your code cares what get(0) returns then something else breaks (often before even verify() gets executed).
		 //If your code doesn't care what get(0) returns then it should not be stubbed. Not convinced? See here.
		 verify(mockedList).get(0);

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testArgMatcher()
	{
		List<String> mockedList = mock(List.class);
		 //stubbing using built-in anyInt() argument matcher
		 when(mockedList.get(anyInt())).thenReturn("element");
		 
		 //following prints "element"
		 System.out.println(mockedList.get(999));
		 
		 //you can also verify using an argument matcher
		 verify(mockedList).get(anyInt());

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testExactNumberOfCalls()
	{
		List<String> mockedList = mock(List.class);
		//using mock 
		 mockedList.add("once");
		 
		 mockedList.add("twice");
		 mockedList.add("twice");
		 
		 mockedList.add("three times");
		 mockedList.add("three times");
		 mockedList.add("three times");
		 
		 //following two verifications work exactly the same - times(1) is used by default
		 verify(mockedList).add("once");
		 verify(mockedList, times(1)).add("once");
		 
		 //exact number of invocations verification
		 verify(mockedList, times(2)).add("twice");
		 verify(mockedList, times(3)).add("three times");
		 
		 //verification using never(). never() is an alias to times(0)
		 verify(mockedList, never()).add("never happened");
		 
		 //verification using atLeast()/atMost()
		 verify(mockedList, atLeastOnce()).add("three times");
//		 verify(mockedList, atLeast(2)).add("five times");// return exception
		 verify(mockedList, atMost(5)).add("three times");

	}
}
