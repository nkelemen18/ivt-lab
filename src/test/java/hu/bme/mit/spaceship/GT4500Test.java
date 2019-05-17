package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private  TorpedoStore mockPTS;
  private  TorpedoStore mockSTS;

  @BeforeEach
  public void init(){
    mockPTS = mock(TorpedoStore.class);
    mockSTS = mock(TorpedoStore.class);

    this.ship = new GT4500(mockPTS, mockSTS);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockPTS.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockPTS, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockPTS, times(1)).fire(1);
    verify(mockSTS, times(1)).fire(1);
  }


  @Test
  public void fireTorpedo_Single_Primary_Empty(){
    // Arrange
    when(mockPTS.isEmpty()).thenReturn(true);
    when(mockSTS.fire(1)).thenReturn(true);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    assertEquals(true, result);
    verify(mockPTS, times(0)).fire(1);
    verify(mockSTS, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Primary_Failure(){
    // Arrange
    when(mockPTS.fire(1)).thenReturn(false);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    assertEquals(false, result);
    verify(mockPTS, times(1)).fire(1);
    verify(mockSTS, times(0)).fire(1);
  }

  //Test primary cooldown
  @Test
  public void fireTorpedo_Single_Twice(){
    // Arrange
    when(mockPTS.fire(1)).thenReturn(true);
    when(mockSTS.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE) && ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockPTS, times(1)).fire(1);
    verify(mockSTS, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_FirstEmpty(){
    // Arrange
    when(mockPTS.isEmpty()).thenReturn(true);
    when(mockSTS.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockPTS, times(0)).fire(1);
    verify(mockSTS, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_SecondEmpty(){
    // Arrange
    when(mockPTS.fire(1)).thenReturn(true);
    when(mockSTS.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockPTS, times(1)).fire(1);
    verify(mockSTS, times(0)).fire(1);
  }


  //Based on the source code
  @Test
  public void fireTorpedo_All_BothEmpty(){
    // Arrange
    when(mockPTS.isEmpty()).thenReturn(true);
    when(mockSTS.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(mockPTS, times(0)).fire(1);
    verify(mockSTS, times(0)).fire(1);
  }


  @Test
  public void fireTorpedo_Single_SecondEmpty(){
    // Arrange
    when(mockPTS.fire(1)).thenReturn(true);
    when(mockPTS.isEmpty()).thenReturn(false);
    when(mockSTS.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE) && ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockPTS, times(2)).fire(1);
    verify(mockSTS, times(0)).fire(1);
  }

  @Test
  public void fireLaser_WithoutDependencyInjection(){
    GT4500 test_ship = new GT4500();

    // Act
    boolean result = test_ship.fireLaser(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
  }


}
