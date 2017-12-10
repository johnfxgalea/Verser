package verser.server.compiler.implementations.jj;

public class StructureCheck extends VisitorResult{

	private boolean isInArray;
	private boolean isOutArray;
	private int elementsInList;
	private int elementsOutList;
	
	public StructureCheck(){
		isInArray = false;
		isOutArray = false;
		elementsInList = 0;
		elementsOutList = 0;
	}
	
	public void setElementsInList(int elementsIn){
		elementsInList = elementsIn;
	}
	
	public void setElementsOutList(int elementsOut){
		elementsOutList = elementsOut;
	}
	
	public void setOutList(){
		isOutArray = true;
	}
	
	public void setInList(){
		isInArray = true;
	}
	
	public boolean isArrayStructureCorrect(){
		System.out.println("Checking Array: " + isOutArray + " " + isInArray + " " + elementsInList + " " + elementsOutList );
		return ( (!(isOutArray && isInArray)) || (elementsInList == elementsOutList));
	}
	
	public void propagateCheckData(StructureCheck check){
		if (!isInArray){
			isInArray = check.isInArray;
			elementsInList = check.elementsInList;
		}
		
		if (!isOutArray){
			isOutArray = check.isOutArray;
			elementsOutList = check.elementsOutList;
		}
		
		if (success){
			success = check.success;
		}
	}
	
}
