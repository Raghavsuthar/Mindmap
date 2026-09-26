package com.example

import com.example.data.NeuroMapRepository
import com.example.data.model.EntityLayer
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testNeuroMapRepository_circuitsCountAndIntegrity() {
    val circuits = NeuroMapRepository.circuits
    assertTrue("Should have at least 6 circuits", circuits.size >= 6)
    circuits.forEach { circuit ->
      assertTrue("Circuit ID should not be blank", circuit.id.isNotBlank())
      assertTrue("Circuit name should not be blank", circuit.name.isNotBlank())
      assertTrue("Circuit should have brain structures", circuit.brainStructures.isNotEmpty())
      assertTrue("Circuit should have key nodes for the map", circuit.keyNodes.isNotEmpty())
    }
  }

  @Test
  fun testNeuroMapRepository_neurotransmittersCountAndReceptors() {
    val nts = NeuroMapRepository.neurotransmitters
    assertTrue("Should have at least 6 neurotransmitters", nts.size >= 6)
    nts.forEach { nt ->
      assertTrue("NT should have symbol", nt.symbol.isNotBlank())
      assertTrue("NT should have receptor subtypes", nt.receptorSubtypes.isNotEmpty())
      assertTrue("NT should have major pathways", nt.majorPathways.isNotEmpty())
    }
  }

  @Test
  fun testNeuroMapRepository_syndromesCodesAndCriteria() {
    val syndromes = NeuroMapRepository.syndromes
    assertTrue("Should have at least 10 syndromes", syndromes.size >= 10)
    syndromes.forEach { syn ->
      assertTrue("Should have valid ICD-11 code", syn.icd11Code.isNotBlank())
      assertTrue("Should have valid DSM-5 code", syn.dsm5Code.isNotBlank())
      assertTrue("Should have linked circuits", syn.linkedCircuits.isNotEmpty())
      assertTrue("Should have linked drugs", syn.linkedDrugs.isNotEmpty())
    }
  }

  @Test
  fun testNeuroMapRepository_drugsCountAndReceptorProfiles() {
    val drugs = NeuroMapRepository.drugs
    assertTrue("Should have at least 15 psychotropic drugs", drugs.size >= 15)
    drugs.forEach { drug ->
      assertTrue("Drug should have brand name", drug.brandName.isNotBlank())
      assertTrue("Drug should have ATC code", drug.atcCode.isNotBlank())
      assertTrue("Drug should have receptor targets", drug.receptorTargets.isNotEmpty())
      assertTrue("Drug should have dosing range", drug.dosingRange.targetDose.isNotBlank())
      assertTrue("Drug should have pharmacokinetics", drug.pharmacokinetics.halfLife.isNotBlank())
    }
  }

  @Test
  fun testNeuroMapRepository_searchFunctionality() {
    val allResults = NeuroMapRepository.searchAll("serotonin", null)
    assertTrue("Search for serotonin should return results across layers", allResults.isNotEmpty())

    val drugResults = NeuroMapRepository.searchAll("sertraline", EntityLayer.DRUGS)
    assertEquals(1, drugResults.size)
    assertEquals("sertraline", drugResults[0].id)
  }
}

