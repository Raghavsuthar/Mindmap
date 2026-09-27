package com.example.data

import com.example.data.model.*

object NeuroMapRepository {

    val brainRegions: List<BrainRegion> = listOf(
        BrainRegion(
            id = "dlpfc",
            name = "Dorsolateral Prefrontal Cortex",
            abbreviation = "DLPFC",
            anatomicalGroup = "Neocortex",
            role = "Executive function, working memory, cognitive flexibility, top-down attentional control",
            xPercent = 0.22f,
            yPercent = 0.25f,
            associatedCircuits = listOf("cstc_loop", "mesocortical_pathway", "default_mode_network")
        ),
        BrainRegion(
            id = "vmpfc",
            name = "Ventromedial Prefrontal Cortex",
            abbreviation = "vmPFC",
            anatomicalGroup = "Neocortex / Limbic Interface",
            role = "Value-based decision making, emotional valence, fear extinction processing",
            xPercent = 0.18f,
            yPercent = 0.44f,
            associatedCircuits = listOf("frontolimbic_circuit", "default_mode_network", "cstc_loop")
        ),
        BrainRegion(
            id = "ofc",
            name = "Orbitofrontal Cortex",
            abbreviation = "OFC",
            anatomicalGroup = "Neocortex",
            role = "Compulsion loop, reward expectation, reversal learning, error prediction",
            xPercent = 0.20f,
            yPercent = 0.56f,
            associatedCircuits = listOf("cstc_loop", "salience_network")
        ),
        BrainRegion(
            id = "dacc",
            name = "Dorsal Anterior Cingulate Cortex",
            abbreviation = "dACC",
            anatomicalGroup = "Cingulate Gyrus",
            role = "Conflict monitoring, error detection, pain processing, autonomic coordination",
            xPercent = 0.38f,
            yPercent = 0.30f,
            associatedCircuits = listOf("salience_network", "cstc_loop")
        ),
        BrainRegion(
            id = "insula",
            name = "Anterior Insula",
            abbreviation = "aINS",
            anatomicalGroup = "Paralimbic Cortex",
            role = "Interoceptive awareness, subjective emotional experience, disgust, visceral salience",
            xPercent = 0.40f,
            yPercent = 0.50f,
            associatedCircuits = listOf("salience_network")
        ),
        BrainRegion(
            id = "pcc",
            name = "Posterior Cingulate Cortex / Precuneus",
            abbreviation = "PCC",
            anatomicalGroup = "Cingulate Cortex",
            role = "Core hub of Default Mode Network; self-referential thought, autobiographical memory, rumination",
            xPercent = 0.76f,
            yPercent = 0.35f,
            associatedCircuits = listOf("default_mode_network")
        ),
        BrainRegion(
            id = "caudate",
            name = "Dorsal Striatum (Caudate / Putamen)",
            abbreviation = "DS",
            anatomicalGroup = "Basal Ganglia",
            role = "Motor gating, habit formation, repetitive behaviors, stereotypy in OCD and tic disorders",
            xPercent = 0.42f,
            yPercent = 0.40f,
            associatedCircuits = listOf("cstc_loop")
        ),
        BrainRegion(
            id = "nacc",
            name = "Nucleus Accumbens (Ventral Striatum)",
            abbreviation = "NAcc",
            anatomicalGroup = "Basal Ganglia",
            role = "Reward processing, hedonic tone, incentive salience, anticipation, addiction circuits",
            xPercent = 0.35f,
            yPercent = 0.53f,
            associatedCircuits = listOf("mesolimbic_pathway")
        ),
        BrainRegion(
            id = "amygdala",
            name = "Amygdala",
            abbreviation = "AMY",
            anatomicalGroup = "Limbic System",
            role = "Threat detection, conditioned fear response, emotional salience, vigilance",
            xPercent = 0.46f,
            yPercent = 0.60f,
            associatedCircuits = listOf("frontolimbic_circuit", "mesolimbic_pathway", "hpa_axis")
        ),
        BrainRegion(
            id = "hippocampus",
            name = "Hippocampus",
            abbreviation = "HIP",
            anatomicalGroup = "Limbic System",
            role = "Declarative episodic memory, contextual threat encoding, adult neurogenesis, stress feedback",
            xPercent = 0.62f,
            yPercent = 0.58f,
            associatedCircuits = listOf("frontolimbic_circuit", "hpa_axis", "default_mode_network")
        ),
        BrainRegion(
            id = "thalamus",
            name = "Thalamus (Mediodorsal / VA / VL)",
            abbreviation = "THAL",
            anatomicalGroup = "Diencephalon",
            role = "Sensory and cortical relay filter; gates thalamocortical reverberation in psychoses and obsessions",
            xPercent = 0.54f,
            yPercent = 0.46f,
            associatedCircuits = listOf("cstc_loop", "salience_network")
        ),
        BrainRegion(
            id = "hypothalamus",
            name = "Hypothalamus (PVN)",
            abbreviation = "HYP",
            anatomicalGroup = "Diencephalon",
            role = "Neuroendocrine master regulator (CRH release), autonomic tone, circadian and appetite pacing",
            xPercent = 0.48f,
            yPercent = 0.54f,
            associatedCircuits = listOf("hpa_axis")
        ),
        BrainRegion(
            id = "vta",
            name = "Ventral Tegmental Area",
            abbreviation = "VTA",
            anatomicalGroup = "Midbrain",
            role = "Origin of mesolimbic & mesocortical DA pathways; reward prediction error and drive",
            xPercent = 0.52f,
            yPercent = 0.66f,
            associatedCircuits = listOf("mesolimbic_pathway", "mesocortical_pathway")
        ),
        BrainRegion(
            id = "sn",
            name = "Substantia Nigra (Pars Compacta)",
            abbreviation = "SNc",
            anatomicalGroup = "Midbrain",
            role = "Origin of nigrostriatal dopamine pathway; motor facilitation, extrapyramidal symptoms (EPS)",
            xPercent = 0.56f,
            yPercent = 0.68f,
            associatedCircuits = listOf("cstc_loop")
        ),
        BrainRegion(
            id = "locus_coeruleus",
            name = "Locus Coeruleus",
            abbreviation = "LC",
            anatomicalGroup = "Pons / Brainstem",
            role = "Principal source of brain norepinephrine; hypervigilance, autonomic arousal, panic trigger",
            xPercent = 0.60f,
            yPercent = 0.74f,
            associatedCircuits = listOf("salience_network", "frontolimbic_circuit")
        ),
        BrainRegion(
            id = "raphe_nuclei",
            name = "Dorsal & Median Raphe Nuclei",
            abbreviation = "DRN",
            anatomicalGroup = "Midbrain / Pons",
            role = "Principal source of central serotonin (5-HT); mood modulation, impulsivity, sensory gating",
            xPercent = 0.54f,
            yPercent = 0.76f,
            associatedCircuits = listOf("frontolimbic_circuit", "cstc_loop")
        )
    )

    val circuits: List<Circuit> = listOf(
        Circuit(
            id = "cstc_loop",
            name = "Cortico-Striatal-Thalamic-Cortical (CSTC) Loop",
            tierCode = "CIRC-CSTC-01",
            brainStructures = listOf("DLPFC", "vmPFC", "OFC", "Caudate/Putamen", "Thalamus", "SNc"),
            function = "Motor gating, executive filtering, cognitive sequencing, obsessions, and worry modulation",
            description = "Topographically segregated recursive loops connecting the neocortex, basal ganglia, and thalamus. In OCD and GAD, disinhibition of thalamocortical reverberation leads to intrusive thoughts and compulsive motor/cognitive rituals.",
            keyNodes = listOf("dlpfc", "ofc", "caudate", "thalamus"),
            linkedTransmitters = listOf("dopamine", "glutamate", "gaba", "serotonin"),
            linkedSyndromes = listOf("ocd", "gad", "schizophrenia", "adhd"),
            linkedDrugs = listOf("sertraline", "escitalopram", "aripiprazole", "clomipramine", "risperidone")
        ),
        Circuit(
            id = "mesolimbic_pathway",
            name = "Mesolimbic Reward & Incentive Salience Pathway",
            tierCode = "CIRC-MESO-02",
            brainStructures = listOf("Ventral Tegmental Area (VTA)", "Nucleus Accumbens", "Amygdala", "Hippocampus"),
            function = "Reinforcement learning, reward anticipation, incentive salience ('wanting'), positive psychotic symptoms",
            description = "Dopaminergic projection from VTA to Nucleus Accumbens. Excessive phasic DA release causes aberrant assignment of salience (delusions/hallucinations in schizophrenia), while hypofunction mediates anhedonia and substance dependence.",
            keyNodes = listOf("vta", "nacc", "amygdala", "hippocampus"),
            linkedTransmitters = listOf("dopamine", "glutamate", "gaba"),
            linkedSyndromes = listOf("schizophrenia", "bipolar1", "mdd", "aud"),
            linkedDrugs = listOf("aripiprazole", "risperidone", "olanzapine", "bupropion", "methylphenidate")
        ),
        Circuit(
            id = "mesocortical_pathway",
            name = "Mesocortical Cognitive & Affective Pathway",
            tierCode = "CIRC-MESO-03",
            brainStructures = listOf("Ventral Tegmental Area (VTA)", "DLPFC", "vmPFC"),
            function = "Working memory, executive drive, social cognition, negative symptoms and cognitive deficits",
            description = "Dopaminergic fibers projecting from midbrain VTA to the prefrontal cortex. Hypoactivity in this tract is responsible for the negative (flat affect, avolition) and cognitive symptoms of schizophrenia.",
            keyNodes = listOf("vta", "dlpfc", "vmpfc"),
            linkedTransmitters = listOf("dopamine", "norepinephrine"),
            linkedSyndromes = listOf("schizophrenia", "adhd", "mdd"),
            linkedDrugs = listOf("aripiprazole", "clozapine", "bupropion", "methylphenidate")
        ),
        Circuit(
            id = "default_mode_network",
            name = "Default Mode Network (DMN)",
            tierCode = "CIRC-DMN-04",
            brainStructures = listOf("vmPFC", "Posterior Cingulate Cortex (PCC)", "Precuneus", "Angular Gyrus", "Hippocampus"),
            function = "Self-referential thought, autobiographical memory, mind-wandering, theory of mind",
            description = "Active during passive rest and internally directed attention. In Major Depressive Disorder, hyperconnectivity and failure of task-induced DMN suppression directly drives depressive rumination and negative self-schemas.",
            keyNodes = listOf("vmpfc", "pcc", "hippocampus"),
            linkedTransmitters = listOf("serotonin", "glutamate", "gaba"),
            linkedSyndromes = listOf("mdd", "trd", "ptsd", "bpd"),
            linkedDrugs = listOf("esketamine", "sertraline", "escitalopram", "venlafaxine")
        ),
        Circuit(
            id = "frontolimbic_circuit",
            name = "Fronto-Limbic Emotion Regulation Circuit",
            tierCode = "CIRC-FLIM-05",
            brainStructures = listOf("vmPFC", "DLPFC", "dACC", "Amygdala", "Hippocampus", "Raphe Nuclei"),
            function = "Top-down appraisal and suppression of emotional and threat responses, mood stabilization",
            description = "Bidirectional connections between prefrontal inhibitory hubs and the hyperreactive amygdala. Deficits in prefrontal inhibition allow excessive limbic activation, precipitating panic attacks, trauma re-experiencing, and affective lability.",
            keyNodes = listOf("vmpfc", "dlpfc", "amygdala", "hippocampus", "raphe_nuclei"),
            linkedTransmitters = listOf("serotonin", "norepinephrine", "gaba", "glutamate"),
            linkedSyndromes = listOf("mdd", "gad", "panic_disorder", "ptsd", "bpd"),
            linkedDrugs = listOf("sertraline", "escitalopram", "venlafaxine", "lamotrigine", "buspirone")
        ),
        Circuit(
            id = "salience_network",
            name = "Salience Network",
            tierCode = "CIRC-SALI-06",
            brainStructures = listOf("Anterior Insula", "Dorsal Anterior Cingulate (dACC)", "Thalamus", "Locus Coeruleus"),
            function = "Detecting biologically salient homeostatic stimuli, switching between Central Executive Network and DMN",
            description = "Coordinated by the anterior insula and dACC. Aberrant salience switching underlies somatic anxiety, chronic pain syndromes, emotional hypervigilance in PTSD, and dissociation.",
            keyNodes = listOf("insula", "dacc", "thalamus", "locus_coeruleus"),
            linkedTransmitters = listOf("norepinephrine", "serotonin", "dopamine"),
            linkedSyndromes = listOf("ptsd", "gad", "panic_disorder", "schizophrenia"),
            linkedDrugs = listOf("venlafaxine", "quetiapine", "clonazepam", "aripiprazole")
        ),
        Circuit(
            id = "hpa_axis",
            name = "Hypothalamic-Pituitary-Adrenal (HPA) Axis",
            tierCode = "CIRC-HPA-07",
            brainStructures = listOf("PVN of Hypothalamus", "Anterior Pituitary", "Adrenal Cortex", "Hippocampus", "Amygdala"),
            function = "Neuroendocrine stress response, cortisol feedback, autonomic tone, immune modulation",
            description = "Stress triggers hypothalamic CRH release -> pituitary ACTH -> adrenal cortisol. Chronic hyperactivity induces hippocampal glucocorticoid receptor downregulation, dendritic atrophy, and impaired neurogenesis in treatment-resistant depression.",
            keyNodes = listOf("hypothalamus", "amygdala", "hippocampus"),
            linkedTransmitters = listOf("norepinephrine", "gaba", "serotonin"),
            linkedSyndromes = listOf("mdd", "trd", "ptsd", "insomnia"),
            linkedDrugs = listOf("esketamine", "mirtazapine", "sertraline", "lithium")
        )
    )

    val neurotransmitters: List<Neurotransmitter> = listOf(
        Neurotransmitter(
            id = "serotonin",
            name = "Serotonin (5-HT)",
            symbol = "5-HT",
            description = "Primary indoleamine neurotransmitter synthesized from L-tryptophan by tryptophan hydroxylase. Synthesized in the brainstem dorsal and median raphe nuclei with extensive projections across the neuraxis controlling affect, satiety, sexual function, and impulse control.",
            receptorSubtypes = listOf(
                ReceptorSubtype("5-HT1A", "Gi/o (Inhibitory autoreceptor & post-synaptic)", "Anxiolysis, antidepressant action, autoreceptor somatodendritic inhibition", "Raphe nuclei, Hippocampus, Cortex"),
                ReceptorSubtype("5-HT1D", "Gi/o (Terminal autoreceptor)", "Inhibits 5-HT release, vascular tone regulation", "Cerebral blood vessels, axon terminals"),
                ReceptorSubtype("5-HT2A", "Gq (Excitatory PLC/IP3)", "Target of SGA antagonism (reduces EPS, increases frontal DA), psychedelic hallucination target", "Neocortex Layer V, Basal Ganglia, Platelets"),
                ReceptorSubtype("5-HT2C", "Gq (Excitatory)", "Appetite suppression, DA/NE tonic inhibition in PFC. Antagonism promotes weight gain but enhances DA/NE", "Choroid plexus, Hypothalamus, PFC"),
                ReceptorSubtype("5-HT3", "Ligand-gated ion channel (Cation influx)", "Chemoreceptor trigger zone nausea, GI motility, anxiety modulation", "Area postrema, vagal afferents, Hippocampus"),
                ReceptorSubtype("5-HT7", "Gs (cAMP stimulatory)", "Circadian rhythm pacing, sleep architecture, cognition, antidepressant synergy", "Hypothalamus, Cortex, Thalamus")
            ),
            majorPathways = listOf("Dorsal raphe to Striatum (motor gating)", "Dorsal raphe to Neocortex (affect & cognition)", "Median raphe to Limbic system (anxiety/fear tone)", "Spinal projections (pain, sexual reflexes)"),
            linkedCircuits = listOf("frontolimbic_circuit", "cstc_loop", "default_mode_network", "hpa_axis"),
            linkedSyndromes = listOf("mdd", "gad", "ocd", "panic_disorder", "ptsd", "trd"),
            linkedDrugs = listOf("sertraline", "escitalopram", "venlafaxine", "mirtazapine", "buspirone", "aripiprazole", "clozapine", "olanzapine", "clomipramine")
        ),
        Neurotransmitter(
            id = "dopamine",
            name = "Dopamine (DA)",
            symbol = "DA",
            description = "Catecholamine derived from L-tyrosine via tyrosine hydroxylase and DOPA decarboxylase. Controls reward reinforcement, volition, movement coordination, neuroendocrine prolactin suppression, and working memory gating.",
            receptorSubtypes = listOf(
                ReceptorSubtype("D1", "Gs (Adenylyl cyclase stimulatory)", "Enhances cortical signal-to-noise ratio, cognitive throughput", "Striatum, Neocortex"),
                ReceptorSubtype("D2", "Gi/o (Adenylyl cyclase inhibitory)", "Target of all approved antipsychotics; >65% blockade needed for antipsychotic effect, >80% causes EPS", "Striatum, Limbic system, Pituitary lactotrophs"),
                ReceptorSubtype("D3", "Gi/o (High limbic selectivity)", "Motivation, mood, addiction vulnerability, partial agonism promotes drive", "Islands of Calleja, Nucleus Accumbens"),
                ReceptorSubtype("D4", "Gi/o (Preferential cortical)", "Novelty seeking, attention, highly bound by clozapine", "Frontal cortex, Hippocampus"),
                ReceptorSubtype("D5", "Gs (Stimulatory)", "Cognitive modulation and synaptic plasticity", "Hippocampus, Hypothalamus")
            ),
            majorPathways = listOf("Mesolimbic (VTA -> NAcc): reward & positive symptoms", "Mesocortical (VTA -> Cortex): motivation & negative symptoms", "Nigrostriatal (SN -> Striatum): EPS & movement", "Tuberoinfundibular (Hypothalamus -> Pituitary): prolactin control"),
            linkedCircuits = listOf("mesolimbic_pathway", "mesocortical_pathway", "cstc_loop", "salience_network"),
            linkedSyndromes = listOf("schizophrenia", "bipolar1", "adhd", "mdd", "aud"),
            linkedDrugs = listOf("aripiprazole", "risperidone", "olanzapine", "quetiapine", "clozapine", "bupropion", "methylphenidate")
        ),
        Neurotransmitter(
            id = "norepinephrine",
            name = "Norepinephrine (NE)",
            symbol = "NE",
            description = "Central catecholamine synthesized from dopamine by dopamine beta-hydroxylase. The locus coeruleus contains >50% of brain noradrenergic neurons, maintaining alertness, cardiovascular tone, stress responsiveness, and cognitive focus.",
            receptorSubtypes = listOf(
                ReceptorSubtype("Alpha-1", "Gq (Vasoconstriction & excitation)", "Sympathetic arousal; blockade causes orthostatic hypotension, reflex tachycardia, and sedation", "Vascular smooth muscle, Cortex"),
                ReceptorSubtype("Alpha-2A", "Gi/o (Pre-synaptic autoreceptor & post-synaptic PFC)", "Inhibits NE release; post-synaptic stimulation in PFC improves working memory and ADHD symptoms", "Locus Coeruleus, Prefrontal Cortex"),
                ReceptorSubtype("Beta-1", "Gs (Cardiac & cortical stimulation)", "Heart rate, inotropy; central arousal and tremors", "Heart, Cortex, Striatum"),
                ReceptorSubtype("Beta-2", "Gs (Bronchodilation & tremor)", "Peripheral bronchodilation, somatic anxiety symptoms", "Lungs, skeletal muscle, brain")
            ),
            majorPathways = listOf("Locus coeruleus ascending to Frontal Cortex (attention & focus)", "Locus coeruleus to Limbic structures (panic, trauma recall)", "Descending bulbospinal tract (nociception gate)"),
            linkedCircuits = listOf("salience_network", "frontolimbic_circuit", "hpa_axis", "mesocortical_pathway"),
            linkedSyndromes = listOf("gad", "ptsd", "panic_disorder", "adhd", "mdd"),
            linkedDrugs = listOf("venlafaxine", "bupropion", "mirtazapine", "quetiapine", "methylphenidate")
        ),
        Neurotransmitter(
            id = "gaba",
            name = "GABA (Gamma-Aminobutyric Acid)",
            symbol = "GABA",
            description = "Chief inhibitory neurotransmitter of the vertebrate central nervous system, synthesized from glutamate via glutamic acid decarboxylase (GAD). Maintains neural inhibition and prevents excitotoxic or seizure activity.",
            receptorSubtypes = listOf(
                ReceptorSubtype("GABA-A", "Ligand-gated Cl- channel (Pentamer)", "Rapid hyperpolarization; positive allosteric site for Benzodiazepines, Barbiturates, Z-drugs", "Ubiquitous central neurons, interneurons"),
                ReceptorSubtype("GABA-B", "Gi/o (G-protein coupled heterodimer)", "Slow, prolonged inhibitory tone; activates inward rectifying K+ channels, reduces presynaptic Ca2+", "Hippocampus, Cortex, Spinal cord")
            ),
            majorPathways = listOf("Local cortical interneurons (chandelier and basket cells maintaining gamma oscillations)", "Striatal medium spiny neurons to Globus Pallidus", "Purkinje cells to deep cerebellar nuclei"),
            linkedCircuits = listOf("cstc_loop", "frontolimbic_circuit", "mesolimbic_pathway"),
            linkedSyndromes = listOf("gad", "panic_disorder", "insomnia", "bipolar1", "aud"),
            linkedDrugs = listOf("clonazepam", "divalproex", "quetiapine", "olanzapine")
        ),
        Neurotransmitter(
            id = "glutamate",
            name = "Glutamate (Glu)",
            symbol = "Glu",
            description = "Principal excitatory neurotransmitter in the mammalian brain, present in >50% of central synapses. Crucial for long-term potentiation (LTP), synaptic plasticity, and network synchrony, but excessive levels cause neurotoxicity.",
            receptorSubtypes = listOf(
                ReceptorSubtype("NMDA (GluN1/2)", "Ligand/voltage-gated Ca2+ channel (Mg2+ block)", "Synaptic plasticity, memory formation, target of ketamine antagonism", "Cortex, Hippocampus, Striatum"),
                ReceptorSubtype("AMPA (GluA1-4)", "Ligand-gated Na+/K+ channel", "Fast basal excitatory transmission, AMPA throughput elevation key to rapid ketamine antidepressant effects", "Widespread CNS"),
                ReceptorSubtype("mGluR1-8", "G-protein coupled (Group I Gq, Group II/III Gi)", "Presynaptic autoinhibition and modulatory tuning of excitatory tone", "Presynaptic terminals, glial membranes")
            ),
            majorPathways = listOf("Cortico-cortical projection neurons", "Cortico-striatal pyramidal pathways", "Perforant path in Hippocampus (LTP circuit)"),
            linkedCircuits = listOf("cstc_loop", "default_mode_network", "frontolimbic_circuit", "mesolimbic_pathway"),
            linkedSyndromes = listOf("mdd", "trd", "schizophrenia", "bipolar1"),
            linkedDrugs = listOf("esketamine", "lamotrigine", "divalproex", "lithium")
        ),
        Neurotransmitter(
            id = "acetylcholine",
            name = "Acetylcholine (ACh)",
            symbol = "ACh",
            description = "Synthesized from choline and acetyl-CoA by choline acetyltransferase (ChAT). Mediates cortical arousal, attention, encoding of new memories, and autonomic parasympathetic outflow.",
            receptorSubtypes = listOf(
                ReceptorSubtype("M1 (Muscarinic)", "Gq (G-protein coupled)", "Cognition, memory, salivary/lacrimal secretion. Antagonism causes memory blur, dry mouth, constipation", "Cortex, Hippocampus, Glands"),
                ReceptorSubtype("M2/M4", "Gi/o (Inhibitory)", "Autoreceptor feedback and striatal modulation", "Heart (M2), Striatum (M4)"),
                ReceptorSubtype("Alpha-4-Beta-2 Nicotinic", "Ligand-gated cation channel", "DA release in NAcc, attention, nicotine dependence target", "Dopaminergic and cortical terminals"),
                ReceptorSubtype("Alpha-7 Nicotinic", "Ligand-gated high Ca2+ channel", "Sensory gating, auditory P50 filtering deficits in schizophrenia", "Hippocampus, Cortex")
            ),
            majorPathways = listOf("Nucleus Basalis of Meynert to widespread Neocortex", "Septal nuclei to Hippocampus (theta rhythm generation)", "Pontomesencephalotegmental complex to Thalamus"),
            linkedCircuits = listOf("default_mode_network", "cstc_loop"),
            linkedSyndromes = listOf("schizophrenia", "mdd", "insomnia"),
            linkedDrugs = listOf("clozapine", "olanzapine", "quetiapine")
        )
    )

    val syndromes: List<Syndrome> = listOf(
        Syndrome(
            id = "mdd",
            name = "Major Depressive Disorder",
            icd11Code = "6A70",
            dsm5Code = "296.2x / 296.3x",
            category = "Mood Disorders",
            clinicalDefinition = "A persistent syndrome characterized by at least 2 weeks of pervasive depressed mood or anhedonia, accompanied by vegetative, cognitive, and somatic disturbances (sleep, appetite, fatigue, psychomotor changes, guilt, suicidal ideation).",
            primaryPathophysiologicalHallmark = "DMN hyperconnectivity causing unrelenting rumination; fronto-limbic hypoconnectivity (impaired DLPFC top-down suppression of limbic distress); monoaminergic depletion (5-HT/NE/DA) and neurotrophic BDNF deficit.",
            linkedCircuits = listOf("default_mode_network", "frontolimbic_circuit", "mesolimbic_pathway", "hpa_axis"),
            linkedTransmitters = listOf("serotonin", "norepinephrine", "dopamine", "glutamate"),
            linkedDrugs = listOf("sertraline", "escitalopram", "venlafaxine", "bupropion", "mirtazapine", "aripiprazole", "esketamine"),
            summary = "Major depressive disorder is a syndrome of persistent low mood or loss of pleasure lasting at least two weeks, accompanied by changes in sleep, appetite, energy, concentration and self-worth, with suicide risk assessment essential at every contact. Current models implicate dysregulation across monoamine, glutamate and stress-axis systems together with hyperactivity of self-referential brain networks. First-line care combines an evidence-based antidepressant with structured psychotherapy, escalating to augmentation or neuromodulation when response is partial.",
            sources = listOf(Source("IPS CPG 2025 update (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC12900045"), Source("CANMAT 2023 MDD guideline (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC11351064/"), Source("NICE mental health guidance index", "https://www.nice.org.uk/guidance/conditions-and-diseases/mental-health-behavioural-and-neurodevelopmental-conditions"), Source("WHO ICD-11 browser", "https://icd.who.int/browse11/l-m/en"))
        ),
        Syndrome(
            id = "bipolar1",
            name = "Bipolar I Disorder, Manic Episode",
            icd11Code = "6A60",
            dsm5Code = "296.4x",
            category = "Mood Disorders",
            clinicalDefinition = "A distinct period of abnormally and persistently elevated, expansive, or irritable mood with abnormally increased activity or energy lasting at least 1 week, causing marked functional impairment or psychotic features.",
            primaryPathophysiologicalHallmark = "Impaired prefrontal-striatal gating; excessive intracellular secondary messenger signaling (PKC, IP3/DAG cascades); hyperactive mesolimbic dopamine driving grandiosity and impulsivity.",
            linkedCircuits = listOf("mesolimbic_pathway", "cstc_loop", "frontolimbic_circuit"),
            linkedTransmitters = listOf("dopamine", "glutamate", "gaba"),
            linkedDrugs = listOf("lithium", "divalproex", "olanzapine", "quetiapine", "aripiprazole", "risperidone"),
            summary = "Bipolar I disorder is defined by at least one manic episode of abnormally elevated or irritable mood with increased energy, impulsivity and often psychosis, usually alternating with depressive episodes. Management separates acute-phase control from long-term relapse prevention, where lithium and several second-generation antipsychotics carry the strongest maintenance evidence. Because antidepressant monotherapy can precipitate mania, mood stabilisation is the backbone of pharmacotherapy.",
            sources = listOf(Source("IPS CPG 2025 update (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC12900045"), Source("CANMAT guidelines", "https://www.canmat.org/"), Source("NICE mental health guidance index", "https://www.nice.org.uk/guidance/conditions-and-diseases/mental-health-behavioural-and-neurodevelopmental-conditions"), Source("WHO ICD-11 browser", "https://icd.who.int/browse11/l-m/en"))
        ),
        Syndrome(
            id = "schizophrenia",
            name = "Schizophrenia",
            icd11Code = "6A20",
            dsm5Code = "295.90",
            category = "Psychotic Disorders",
            clinicalDefinition = "A severe psychiatric disorder lasting >=6 months characterized by positive symptoms (delusions, hallucinations, disorganized speech/behavior) and negative symptoms (avolition, flat affect, asociality) with marked cognitive decline.",
            primaryPathophysiologicalHallmark = "Mesolimbic hyperdopaminergia (D2 overstimulation mediating aberrant salience / hallucinations) coupled with mesocortical hypodopaminergia and NMDA hypofunction on cortical GABAergic interneurons.",
            linkedCircuits = listOf("mesolimbic_pathway", "mesocortical_pathway", "cstc_loop", "salience_network"),
            linkedTransmitters = listOf("dopamine", "glutamate", "gaba", "serotonin"),
            linkedDrugs = listOf("risperidone", "olanzapine", "aripiprazole", "quetiapine", "clozapine"),
            summary = "Schizophrenia is a chronic psychotic illness combining positive symptoms such as delusions and hallucinations with negative symptoms, disorganisation and cognitive impairment lasting six months or more. The prevailing model pairs mesolimbic dopamine overactivity with cortical glutamate-GABA dysregulation. All licensed antipsychotics dampen dopamine D2 signalling to varying degrees; clozapine is reserved for treatment resistance, and long-acting injectables address the adherence gap behind most relapses.",
            sources = listOf(Source("IPS CPG 2025 update (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC12900045"), Source("NICE mental health guidance index", "https://www.nice.org.uk/guidance/conditions-and-diseases/mental-health-behavioural-and-neurodevelopmental-conditions"), Source("WHO ICD-11 browser", "https://icd.who.int/browse11/l-m/en"), Source("APA DSM-5-TR overview", "https://www.psychiatry.org/psychiatrists/practice/dsm"))
        ),
        Syndrome(
            id = "gad",
            name = "Generalized Anxiety Disorder",
            icd11Code = "6B00",
            dsm5Code = "300.02",
            category = "Anxiety Disorders",
            clinicalDefinition = "Excessive anxiety and worry about a number of events or activities, occurring more days than not for at least 6 months, difficult to control and accompanied by somatic symptoms of muscle tension, restlessness, and fatigue.",
            primaryPathophysiologicalHallmark = "Dysfunctional CSTC loop gating ('worry loop') and frontolimbic hypoconnectivity leading to uninhibited amygdalar fear output and central noradrenergic hyper-responsiveness.",
            linkedCircuits = listOf("cstc_loop", "frontolimbic_circuit", "salience_network"),
            linkedTransmitters = listOf("serotonin", "gaba", "norepinephrine"),
            linkedDrugs = listOf("escitalopram", "sertraline", "venlafaxine", "buspirone", "clonazepam"),
            summary = "Generalised anxiety disorder is excessive, hard-to-control worry across multiple domains for six months or more, with muscle tension, restlessness, fatigue and disturbed sleep. First-line pharmacotherapy uses SSRIs or SNRIs, with cognitive-behavioural therapy carrying equal long-term weight, while benzodiazepines stay short-term only because of dependence. Noradrenergic hyperarousal with impaired prefrontal inhibition of threat circuits is the core mechanistic target.",
            sources = listOf(Source("NICE mental health guidance index", "https://www.nice.org.uk/guidance/conditions-and-diseases/mental-health-behavioural-and-neurodevelopmental-conditions"), Source("CANMAT guidelines", "https://www.canmat.org/"), Source("WHO ICD-11 browser", "https://icd.who.int/browse11/l-m/en"))
        ),
        Syndrome(
            id = "panic_disorder",
            name = "Panic Disorder",
            icd11Code = "6B01",
            dsm5Code = "300.01",
            category = "Anxiety Disorders",
            clinicalDefinition = "Recurrent unexpected panic attacks—abrupt surges of intense fear or discomfort peaking within minutes—with persistent worry about additional attacks or maladaptive avoidance behavior.",
            primaryPathophysiologicalHallmark = "Hypersensitive brainstem alarm system (locus coeruleus noradrenergic firing and parabrachial nucleus) triggered by faulty interoceptive signaling from anterior insula with inadequate ventromedial PFC suppression.",
            linkedCircuits = listOf("salience_network", "frontolimbic_circuit"),
            linkedTransmitters = listOf("norepinephrine", "serotonin", "gaba"),
            linkedDrugs = listOf("sertraline", "escitalopram", "venlafaxine", "clonazepam"),
            summary = "Panic disorder pairs recurrent unexpected panic attacks with persistent worry about further attacks and avoidance of triggering contexts. SSRIs are first-line drugs and exposure-based CBT the leading psychotherapy, while a hypersensitive brainstem alarm system driven by noradrenergic surges explains the abrupt autonomic storm. Treatment targets both attack prevention and the dismantling of anticipatory avoidance.",
            sources = listOf(Source("NICE mental health guidance index", "https://www.nice.org.uk/guidance/conditions-and-diseases/mental-health-behavioural-and-neurodevelopmental-conditions"), Source("CANMAT guidelines", "https://www.canmat.org/"), Source("WHO ICD-11 browser", "https://icd.who.int/browse11/l-m/en"))
        ),
        Syndrome(
            id = "ocd",
            name = "Obsessive-Compulsive Disorder",
            icd11Code = "6B20",
            dsm5Code = "300.3",
            category = "OCD-Spectrum Disorders",
            clinicalDefinition = "Presence of obsessions (intrusive, persistent thoughts/urges causing marked anxiety) and/or compulsions (repetitive behaviors or mental acts aimed at preventing distress according to rigid rules).",
            primaryPathophysiologicalHallmark = "Hyperactivity in the direct pathway of the orbitofrontal-striatal-thalamic loop ('habit and error detection loop'), creating a persistent 'feeling of incompleteness' unquenched by motor action.",
            linkedCircuits = listOf("cstc_loop"),
            linkedTransmitters = listOf("serotonin", "dopamine", "glutamate"),
            linkedDrugs = listOf("sertraline", "escitalopram", "aripiprazole", "risperidone", "clomipramine"),
            summary = "Obsessive-compulsive disorder combines intrusive unwanted thoughts or urges with repetitive rituals performed to neutralise distress, driven by hyperactivity in orbitofrontal-striatal-thalamic loops. High-dose SSRIs and clomipramine are the pharmacologic mainstays, and exposure with response prevention is the psychotherapy of choice. Dopamine-blocking augmentation helps the substantial minority with partial SSRI response, especially with comorbid tics.",
            sources = listOf(Source("IPS CPG 2025 update (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC12900045"), Source("NICE mental health guidance index", "https://www.nice.org.uk/guidance/conditions-and-diseases/mental-health-behavioural-and-neurodevelopmental-conditions"), Source("WHO ICD-11 browser", "https://icd.who.int/browse11/l-m/en"))
        ),
        Syndrome(
            id = "ptsd",
            name = "Post-Traumatic Stress Disorder",
            icd11Code = "6B40",
            dsm5Code = "309.81",
            category = "Trauma & Stressor Disorders",
            clinicalDefinition = "Development of characteristic symptoms following exposure to actual or threatened death, serious injury, or sexual violence: intrusive re-experiencing, persistent avoidance of trauma cues, negative alterations in cognition/mood, and marked hyperarousal.",
            primaryPathophysiologicalHallmark = "Failure of ventromedial PFC to exert top-down extinction of conditioned fear memories in the hyperactive amygdala, paired with hippocampal volume loss impairing contextual memory placement.",
            linkedCircuits = listOf("frontolimbic_circuit", "salience_network", "hpa_axis", "default_mode_network"),
            linkedTransmitters = listOf("norepinephrine", "serotonin", "glutamate", "gaba"),
            linkedDrugs = listOf("sertraline", "venlafaxine", "mirtazapine", "quetiapine"),
            summary = "Post-traumatic stress disorder follows exposure to threatened death or serious injury with intrusive re-experiencing, avoidance, negative mood-cognition shifts and hyperarousal. Trauma-focused psychotherapies carry the strongest evidence; SSRIs and SNRIs are first-line drugs, with adrenergic strategies used for nightmares and hyperarousal. Failed prefrontal extinction of amygdala fear memory is the central circuit model.",
            sources = listOf(Source("NICE mental health guidance index", "https://www.nice.org.uk/guidance/conditions-and-diseases/mental-health-behavioural-and-neurodevelopmental-conditions"), Source("WFSBP guidelines", "https://www.wfsbp.org"), Source("WHO ICD-11 browser", "https://icd.who.int/browse11/l-m/en"))
        ),
        Syndrome(
            id = "adhd",
            name = "Attention-Deficit/Hyperactivity Disorder",
            icd11Code = "6A05",
            dsm5Code = "314.0x",
            category = "Neurodevelopmental Disorders",
            clinicalDefinition = "A persistent pattern of inattention and/or hyperactivity-impulsivity that interferes with functioning or development, present prior to age 12 across multiple settings.",
            primaryPathophysiologicalHallmark = "Hypofunction of dopamine and norepinephrine signaling in the dorsolateral and orbitofrontal prefrontal cortex, leading to sub-optimal signal-to-noise ratio in executive attention networks.",
            linkedCircuits = listOf("mesocortical_pathway", "cstc_loop"),
            linkedTransmitters = listOf("dopamine", "norepinephrine"),
            linkedDrugs = listOf("methylphenidate", "bupropion"),
            summary = "Attention-deficit/hyperactivity disorder is a neurodevelopmental syndrome of persistent inattention or hyperactivity-impulsivity impairing function across settings from before age twelve. Stimulants that raise prefrontal dopamine and norepinephrine signalling are first-line, with atomoxetine, guanfacine and bupropion as alternatives. Management pairs medication with behavioural, academic and sleep-hygiene interventions.",
            sources = listOf(Source("NICE mental health guidance index", "https://www.nice.org.uk/guidance/conditions-and-diseases/mental-health-behavioural-and-neurodevelopmental-conditions"), Source("WHO ICD-11 browser", "https://icd.who.int/browse11/l-m/en"), Source("PubMed", "https://pubmed.ncbi.nlm.nih.gov/"))
        ),
        Syndrome(
            id = "bpd",
            name = "Borderline Personality Disorder",
            icd11Code = "6D11.5",
            dsm5Code = "301.83",
            category = "Personality Disorders",
            clinicalDefinition = "A pervasive pattern of instability in interpersonal relationships, self-image, and affects, along with marked impulsivity beginning by early adulthood.",
            primaryPathophysiologicalHallmark = "Marked frontolimbic hypoconnectivity (PFC-amygdala disconnection) coupled with altered endogenous opioid and serotonergic neurotransmission predisposing to intense abandonment panic and chronic affective instability.",
            linkedCircuits = listOf("frontolimbic_circuit", "default_mode_network"),
            linkedTransmitters = listOf("serotonin", "dopamine", "gaba"),
            linkedDrugs = listOf("lamotrigine", "quetiapine", "aripiprazole", "sertraline"),
            summary = "Borderline personality disorder is a pervasive pattern of unstable relationships, self-image and affect with impulsivity and self-harm, rooted in frontolimbic disconnection and heightened rejection sensitivity. Structured psychotherapies such as dialectical behaviour therapy form the treatment backbone, as no drug is licensed specifically for the disorder. Mood stabilisers and second-generation antipsychotics are used adjunctively for affective instability and impulsivity.",
            sources = listOf(Source("NICE mental health guidance index", "https://www.nice.org.uk/guidance/conditions-and-diseases/mental-health-behavioural-and-neurodevelopmental-conditions"), Source("WHO ICD-11 browser", "https://icd.who.int/browse11/l-m/en"))
        ),
        Syndrome(
            id = "trd",
            name = "Treatment-Resistant Depression (TRD)",
            icd11Code = "6A70.3",
            dsm5Code = "296.3x (Specifier)",
            category = "Mood Disorders",
            clinicalDefinition = "Major Depressive Disorder that has failed to achieve adequate response despite at least two consecutive trials of antidepressant medications of adequate dose and duration from different pharmacological classes.",
            primaryPathophysiologicalHallmark = "Chronic neuroinflammatory state, severe synaptic loss in medial PFC and hippocampus, persistent HPA axis dysregulation with loss of glucocorticoid receptor sensitivity, and glutamatergic signaling deficits.",
            linkedCircuits = listOf("default_mode_network", "hpa_axis", "frontolimbic_circuit"),
            linkedTransmitters = listOf("glutamate", "serotonin", "dopamine"),
            linkedDrugs = listOf("esketamine", "aripiprazole", "quetiapine", "lithium", "olanzapine", "clomipramine"),
            summary = "Treatment-resistant depression denotes major depression failing at least two adequate antidepressant trials from different classes. Options with randomised support include dopamine-partial-agonist augmentation, lithium augmentation, esketamine, repetitive TMS, ECT and structured psychotherapy. Glutamatergic dysfunction, chronic stress-axis dysregulation and synaptic loss distinguish its biology from treatment-responsive depression.",
            sources = listOf(Source("CANMAT 2023 MDD guideline (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC11351064/"), Source("NICE mental health guidance index", "https://www.nice.org.uk/guidance/conditions-and-diseases/mental-health-behavioural-and-neurodevelopmental-conditions"), Source("WHO ICD-11 browser", "https://icd.who.int/browse11/l-m/en"))
        ),
        Syndrome(
            id = "insomnia",
            name = "Insomnia Disorder",
            icd11Code = "7A00",
            dsm5Code = "307.42",
            category = "Sleep-Wake Disorders",
            clinicalDefinition = "A predominant complaint of dissatisfaction with sleep quantity or quality (difficulty initiating, maintaining, or non-restorative sleep) despite adequate opportunity, causing clinically significant daytime impairment.",
            primaryPathophysiologicalHallmark = "Hyperarousal across 24 hours mediated by overactivity of ascending reticular activating systems (histaminergic, noradrenergic, orexinergic) and deficient GABAergic anterior hypothalamic (VLPO) inhibition.",
            linkedCircuits = listOf("hpa_axis", "salience_network"),
            linkedTransmitters = listOf("gaba", "acetylcholine", "norepinephrine"),
            linkedDrugs = listOf("mirtazapine", "quetiapine", "clonazepam"),
            summary = "Insomnia disorder is dissatisfaction with sleep initiation, maintenance or quality despite adequate opportunity, with daytime impairment, driven by round-the-clock hyperarousal and deficient sleep-promoting inhibition. CBT for insomnia is first-line and outperforms drugs long-term; sedating antidepressants and short-course hypnotics are pharmacologic options. Treatment always starts with sleep restriction, stimulus control and circadian hygiene.",
            sources = listOf(Source("NICE mental health guidance index", "https://www.nice.org.uk/guidance/conditions-and-diseases/mental-health-behavioural-and-neurodevelopmental-conditions"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"), Source("WHO ICD-11 browser", "https://icd.who.int/browse11/l-m/en"))
        ),
        Syndrome(
            id = "aud",
            name = "Alcohol Use Disorder",
            icd11Code = "6C40",
            dsm5Code = "303.90",
            category = "Substance-Related Disorders",
            clinicalDefinition = "A problematic pattern of alcohol use leading to clinically significant impairment or distress, characterized by tolerance, withdrawal, craving, and inability to cut down.",
            primaryPathophysiologicalHallmark = "Initial acute positive reinforcement via GABA-A allosteric facilitation and mesolimbic dopamine surge, shifting in chronic dependence to allostatic neuroadaptation with NMDA receptor upregulation, down-regulated GABA, and excessive CRF.",
            linkedCircuits = listOf("mesolimbic_pathway", "frontolimbic_circuit"),
            linkedTransmitters = listOf("gaba", "glutamate", "dopamine"),
            linkedDrugs = listOf("divalproex", "clonazepam"),
            summary = "Alcohol use disorder is a problematic pattern of drinking with tolerance, withdrawal, craving and loss of control, maintained by a shift from GABA-dopamine reward toward glutamate-driven dependence and stress sensitisation. Acamprosate, naltrexone and supervised disulfiram support abstinence alongside motivational and relapse-prevention therapies. Benzodiazepines manage withdrawal itself but are not maintenance treatment because of cross-dependence.",
            sources = listOf(Source("NICE mental health guidance index", "https://www.nice.org.uk/guidance/conditions-and-diseases/mental-health-behavioural-and-neurodevelopmental-conditions"), Source("Cochrane Library mental health collections", "https://www.cochranelibrary.com/collections"), Source("WHO ICD-11 browser", "https://icd.who.int/browse11/l-m/en"))
        ),
        Syndrome(
            id = "social_anxiety",
            name = "Social Anxiety Disorder",
            icd11Code = "6B03",
            dsm5Code = "300.23",
            category = "Anxiety Disorders",
            clinicalDefinition = "Marked fear of social scrutiny with avoidance of social or performance situations lasting at least six months, accompanied by panic-like somatic symptoms on exposure and functional impairment.",
            primaryPathophysiologicalHallmark = "Hyperactive amygdala-insula threat response to social-evaluative cues with deficient prefrontal regulation, on shared serotonergic dysregulation with other anxiety disorders.",
            linkedCircuits = listOf("frontolimbic_circuit", "salience_network"),
            linkedTransmitters = listOf("serotonin", "norepinephrine"),
            linkedDrugs = listOf("sertraline", "escitalopram", "venlafaxine"),
            summary = "Social anxiety disorder is a persistent fear of embarrassment or scrutiny in social and performance situations, maintained by avoidance that prevents corrective learning. SSRIs and SNRIs are first-line drugs and exposure-based CBT the leading psychotherapy, with feared situations ranked into a graded hierarchy. Early onset and comorbidity with depression make active treatment, not reassurance, the standard.",
            sources = listOf(Source("NICE mental health guidance index", "https://www.nice.org.uk/guidance/conditions-and-diseases/mental-health-behavioural-and-neurodevelopmental-conditions"), Source("CANMAT guidelines", "https://www.canmat.org/"), Source("WHO ICD-11 browser", "https://icd.who.int/browse11/l-m/en"))
        ),
        Syndrome(
            id = "pdd",
            name = "Persistent Depressive Disorder",
            icd11Code = "6A72",
            dsm5Code = "300.4",
            category = "Mood Disorders",
            clinicalDefinition = "Chronically depressed mood for at least two years with fewer concurrent symptoms than major depression, often punctuated by superimposed major depressive episodes (double depression).",
            primaryPathophysiologicalHallmark = "The same monoamine and self-referential network dysregulation as major depression in milder chronic form, with strong familial loading in early-onset cases.",
            linkedCircuits = listOf("default_mode_network", "frontolimbic_circuit"),
            linkedTransmitters = listOf("serotonin", "norepinephrine"),
            linkedDrugs = listOf("sertraline", "escitalopram", "venlafaxine"),
            summary = "Persistent depressive disorder (dysthymia) is a chronic low-grade depression lasting years rather than weeks, frequently complicated by superimposed major episodes. Treatment mirrors major depression with SSRIs or SNRIs plus structured psychotherapy, and duration of maintenance is typically longer given the relapsing course. Chronicity itself, not severity, defines the diagnosis and the follow-up plan.",
            sources = listOf(Source("CANMAT 2023 MDD guideline (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC11351064/"), Source("NICE mental health guidance index", "https://www.nice.org.uk/guidance/conditions-and-diseases/mental-health-behavioural-and-neurodevelopmental-conditions"), Source("WHO ICD-11 browser", "https://icd.who.int/browse11/l-m/en"))
        ),
    )

    val drugs: List<Drug> = listOf(
        Drug(
            id = "sertraline",
            genericName = "Sertraline",
            brandName = "Zoloft",
            drugClass = "Selective Serotonin Reuptake Inhibitor (SSRI)",
            atcCode = "N06AB06",
            receptorTargets = listOf(
                ReceptorTarget("SERT (5-HTT)", "Inhibitor", 0.4, "High", "Potent 5-HT reuptake blockade; therapeutic threshold reached at >80% transporter occupancy"),
                ReceptorTarget("DAT", "Inhibitor", 25.0, "Moderate", "Weak dopamine transporter inhibition; provides unique activating/energizing profile distinguishing from other SSRIs"),
                ReceptorTarget("Sigma-1", "Agonist", 32.0, "Moderate", "May contribute to anxiolytic and anti-inflammatory properties in psychotic depression")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "26 hours (active metabolite N-desmethylsertraline: 62-104 hours)",
                bioavailability = "44% (absorption enhanced ~25% by food intake)",
                cypMetabolism = "Substrate of CYP2B6, CYP2C19, CYP3A4, CYP2D6; mild inhibitor of CYP2D6 at high doses",
                timeToPeak = "4.5 to 8.4 hours"
            ),
            dosingRange = DosingRange(
                startingDose = "25 - 50 mg/day",
                targetDose = "50 - 150 mg/day",
                maxDose = "200 mg/day (up to 400 mg/day in specialist refractory OCD)",
                titrationSchedule = "Initiate at 50 mg/day (25 mg in panic/frail elderly); titrate by 25-50 mg increments at 1-2 week intervals."
            ),
            commonSideEffects = listOf("Nausea/diarrhea (GI 5-HT3 activation)", "Insomnia or somnolence", "Sexual dysfunction (ejaculatory delay, anorgasmia)", "Tremor", "Sweating"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("GI upset / Diarrhea", "5-HT3 and 5-HT4 receptor stimulation in enteric nervous system", "Take with meals; transient over 7-14 days"),
                SideEffectMechanism("Sexual Dysfunction", "5-HT2A stimulation inhibiting spinal sexual reflex arc and reducing mesolimbic DA", "Dose reduction, weekend holiday, or add Bupropion 150mg"),
                SideEffectMechanism("Initial Agitation", "Somatodendritic 5-HT2A/2C stimulation prior to autoreceptor downregulation", "Start low (25mg) in panic disorder and titrate slowly")
            ),
            blackBoxWarnings = listOf("Suicidality in children, adolescents, and young adults (<=24 years) during initial phase of treatment."),
            linkedSyndromes = listOf("mdd", "ocd", "panic_disorder", "ptsd", "gad", "social_anxiety"),
            linkedCircuits = listOf("frontolimbic_circuit", "cstc_loop", "default_mode_network"),
            linkedTransmitters = listOf("serotonin", "dopamine"),
            clinicalPearls = "Sertraline's weak DAT inhibition makes it preferred for depression with psychomotor retardation and fatigue. It is the most extensively validated first-line SSRI in cardiac disease (SADHART trial).",
            summary = "Sertraline is a first-line SSRI distinguished by modest dopamine-transporter inhibition that gives it a relatively activating profile within its class. Beyond major depression it is established across OCD, panic disorder, PTSD, social anxiety and PMDD, with the strongest cardiac-safety dataset among SSRIs. Gastrointestinal upset, sexual dysfunction and early agitation are the characteristic tolerability issues.",
            sources = listOf(Source("CANMAT 2023 MDD guideline (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC11351064/"), Source("DrugBank", "https://go.drugbank.com/"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"))
        ),
        Drug(
            id = "escitalopram",
            genericName = "Escitalopram",
            brandName = "Lexapro / Cipralex",
            drugClass = "Selective Serotonin Reuptake Inhibitor (SSRI)",
            atcCode = "N06AB10",
            receptorTargets = listOf(
                ReceptorTarget("SERT (Primary site)", "Inhibitor", 1.1, "High", "High-affinity competitive serotonin transporter inhibition"),
                ReceptorTarget("SERT (Allosteric site)", "Allosteric Modulator", 25.0, "Moderate", "Allosteric binding locks SERT in conformation with delayed dissociation; pure S-enantiomer"),
                ReceptorTarget("5-HT2C", "Antagonist", 2500.0, "Negligible", "No meaningful off-target receptor interaction at clinical doses")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "27 - 32 hours",
                bioavailability = "80% (independent of food)",
                cypMetabolism = "CYP2C19 (major), CYP3A4, CYP2D6; minimal CYP inhibition (cleanest drug interaction profile among SSRIs)",
                timeToPeak = "5.0 hours"
            ),
            dosingRange = DosingRange(
                startingDose = "10 mg/day (5 mg in elderly)",
                targetDose = "10 - 20 mg/day",
                maxDose = "20 mg/day (10 mg in patients >65 years due to QT prolongation risk)",
                titrationSchedule = "Start 10 mg daily; increase to 20 mg daily after minimum 1-2 weeks if tolerated and clinically indicated."
            ),
            commonSideEffects = listOf("Nausea", "Headache", "Fatigue / somnolence", "Anorgasmia / delayed ejaculation", "Excessive yawning"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("QTc Prolongation", "hERG potassium channel blockade at supratherapeutic levels", "Cap dose at 10 mg/day in elderly/hepatic impairment; baseline ECG if cardiac risk"),
                SideEffectMechanism("Sexual Dysfunction", "5-HT2A post-synaptic activation lowering spinal reflex sensitivity", "Switching or augmenting with Bupropion or PDE5 inhibitors")
            ),
            blackBoxWarnings = listOf("Increased risk of suicidal thoughts and behaviors in patients <=24 years."),
            linkedSyndromes = listOf("mdd", "gad", "panic_disorder", "ocd", "social_anxiety"),
            linkedCircuits = listOf("frontolimbic_circuit", "cstc_loop", "default_mode_network"),
            linkedTransmitters = listOf("serotonin"),
            clinicalPearls = "The quintessential 'pure' SSRI with virtually no dopamine, norepinephrine, or anticholinergic off-target binding. Ideal first-line agent when polypharmacy and drug-drug interactions are primary concerns.",
            summary = "Escitalopram is the purified S-enantiomer of citalopram and the most selective serotonin-reuptake inhibitor in routine use, giving it one of the cleanest drug-interaction profiles. It is a first-line option for depression and generalised anxiety, dosed once daily up to 20 mg. QT prolongation caps the dose in older adults and those with cardiac risk.",
            sources = listOf(Source("CANMAT 2023 MDD guideline (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC11351064/"), Source("DrugBank", "https://go.drugbank.com/"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"))
        ),
        Drug(
            id = "venlafaxine",
            genericName = "Venlafaxine",
            brandName = "Effexor XR",
            drugClass = "Serotonin-Norepinephrine Reuptake Inhibitor (SNRI)",
            atcCode = "N06AX16",
            receptorTargets = listOf(
                ReceptorTarget("SERT", "Inhibitor", 30.0, "High", "Inhibited across all therapeutic dose ranges (dominant at <150 mg/day)"),
                ReceptorTarget("NET", "Inhibitor", 650.0, "Moderate", "Norepinephrine reuptake blocked in a dose-dependent fashion, primarily at doses >=150-225 mg/day"),
                ReceptorTarget("DAT", "Inhibitor", 4500.0, "Low", "Weak inhibition at very high doses (>300 mg/day)")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "5 hours (parent drug), 11 hours (active metabolite O-desmethylvenlafaxine)",
                bioavailability = "45%",
                cypMetabolism = "Extensively converted by CYP2D6 to active metabolite Desvenlafaxine; minimal CYP inhibition",
                timeToPeak = "5.5 - 9.0 hours (XR formulation)"
            ),
            dosingRange = DosingRange(
                startingDose = "37.5 - 75 mg/day XR",
                targetDose = "150 - 225 mg/day XR",
                maxDose = "375 mg/day XR (inpatient severe depression)",
                titrationSchedule = "Start 37.5-75 mg daily; increase by 75 mg/day every 1-2 weeks. Dual action achieved reliably above 150 mg/day."
            ),
            commonSideEffects = listOf("Dose-dependent diastolic hypertension", "Nausea", "Hyperhidrosis (excessive sweating)", "Tachycardia", "Severe discontinuation syndrome"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Dose-Dependent Hypertension", "Peripheral noradrenergic vascular stimulation (alpha-1 & beta-1)", "Monitor BP regularly; switch if diastolic >90 mmHg persists"),
                SideEffectMechanism("Severe Discontinuation Syndrome", "Rapid clearance and precipitous drop in central 5-HT/NE tone", "Never stop abruptly; cross-taper with Fluoxetine for protracted withdrawal")
            ),
            blackBoxWarnings = listOf("Suicidality in children and young adults <=24 years."),
            linkedSyndromes = listOf("mdd", "gad", "panic_disorder", "ptsd", "trd", "social_anxiety"),
            linkedCircuits = listOf("default_mode_network", "frontolimbic_circuit", "salience_network"),
            linkedTransmitters = listOf("serotonin", "norepinephrine"),
            clinicalPearls = "At low doses (<=75 mg), venlafaxine is essentially an SSRI; true dual-mechanism noradrenergic recruitment occurs above 150 mg/day. Prominent risk of severe 'electric shock' withdrawal sensations if doses are skipped.",
            summary = "Venlafaxine is an SNRI that behaves as an SSRI at low doses and recruits norepinephrine reuptake blockade above roughly 150 mg daily. It covers depression, generalised anxiety, panic and social anxiety but demands blood-pressure monitoring and a slow taper because of prominent discontinuation symptoms. Its short half-life makes missed doses symptomatic within a day.",
            sources = listOf(Source("CANMAT 2023 MDD guideline (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC11351064/"), Source("DrugBank", "https://go.drugbank.com/"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"))
        ),
        Drug(
            id = "bupropion",
            genericName = "Bupropion",
            brandName = "Wellbutrin XL / Zyban",
            drugClass = "Norepinephrine-Dopamine Reuptake Inhibitor (NDRI)",
            atcCode = "N06AX12",
            receptorTargets = listOf(
                ReceptorTarget("DAT", "Inhibitor", 520.0, "Moderate", "Inhibits dopamine reuptake (~20-25% striatal occupancy), enhancing drive and reward"),
                ReceptorTarget("NET", "Inhibitor", 1400.0, "Moderate", "Inhibits norepinephrine reuptake via active metabolites hydroxybupropion"),
                ReceptorTarget("Alpha-3-Beta-4 Nicotinic", "Antagonist", 1800.0, "Moderate", "Antagonism of neuronal nicotinic receptors underpins anti-craving efficacy in smoking cessation")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "21 hours (active metabolites: 20-37 hours)",
                bioavailability = "5-20% (low oral due to extensive hepatic first-pass)",
                cypMetabolism = "CYP2B6 to hydroxybupropion; potent inhibitor of CYP2D6 (doubles Metoprolol, Atomoxetine levels)",
                timeToPeak = "5.0 hours (XL formulation)"
            ),
            dosingRange = DosingRange(
                startingDose = "150 mg/day XL (morning)",
                targetDose = "300 mg/day XL",
                maxDose = "450 mg/day XL",
                titrationSchedule = "Start 150 mg XL in the morning. Increase to 300 mg XL after minimum 4-7 days if tolerated."
            ),
            commonSideEffects = listOf("Insomnia", "Anxiety / agitation", "Weight loss / appetite reduction", "Dry mouth", "Tremor"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Lowered Seizure Threshold", "Direct cortical excitation and GABA inhibition at peak levels", "Strictly contra-indicated in bulimia, anorexia, abrupt alcohol/BZD withdrawal"),
                SideEffectMechanism("Insomnia", "Noradrenergic ascending activation in the locus coeruleus", "Administer exclusively in the morning; avoid late evening doses")
            ),
            blackBoxWarnings = listOf("Suicidality in children/young adults; neuropsychiatric events in smoking cessation (largely revised by EAGLES trial)."),
            linkedSyndromes = listOf("mdd", "adhd", "trd"),
            linkedCircuits = listOf("mesolimbic_pathway", "mesocortical_pathway"),
            linkedTransmitters = listOf("dopamine", "norepinephrine"),
            clinicalPearls = "Zero sexual dysfunction and zero weight gain (often induces weight loss). First-line choice in depression with apathy, hypersomnia, or concurrent ADHD. Absolute contraindication in eating disorders due to seizure risk.",
            summary = "Bupropion is a norepinephrine-dopamine reuptake inhibitor with no sexual side effects and no weight gain, often activating and modestly aiding smoking cessation. It suits depression with fatigue, hypersomnia or attentional features but is strictly contraindicated in eating disorders, seizure history and abrupt alcohol or benzodiazepine withdrawal because it lowers the seizure threshold.",
            sources = listOf(Source("CANMAT 2023 MDD guideline (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC11351064/"), Source("DrugBank", "https://go.drugbank.com/"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"))
        ),
        Drug(
            id = "mirtazapine",
            genericName = "Mirtazapine",
            brandName = "Remeron",
            drugClass = "Noradrenergic & Specific Serotonergic Antidepressant (NaSSA)",
            atcCode = "N06AX11",
            receptorTargets = listOf(
                ReceptorTarget("Alpha-2 Autoreceptor", "Antagonist", 18.0, "High", "Disinhibits presynaptic NE and 5-HT release by blocking autoinhibitory alpha-2 brakes"),
                ReceptorTarget("5-HT2A", "Antagonist", 6.3, "High", "Blocks 5-HT2A: preserves sexual function and reduces anxiety"),
                ReceptorTarget("5-HT2C", "Antagonist", 39.0, "High", "Synergizes with H1 to stimulate voracious appetite and carbohydrate craving"),
                ReceptorTarget("5-HT3", "Antagonist", 7.9, "High", "Complete anti-emetic protection; zero SSRI-like nausea"),
                ReceptorTarget("H1 Histamine", "Antagonist", 0.14, "Very High", "Potent sedation and metabolic orexigenic weight gain (saturates even at 7.5 mg)")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "20 - 40 hours",
                bioavailability = "50%",
                cypMetabolism = "CYP2D6, CYP3A4, CYP1A2; no clinically meaningful inhibition of CYP enzymes",
                timeToPeak = "2.0 hours"
            ),
            dosingRange = DosingRange(
                startingDose = "15 mg/day (bedtime)",
                targetDose = "30 - 45 mg/day",
                maxDose = "45 mg/day",
                titrationSchedule = "Start 15 mg at bedtime. May increase to 30 mg then 45 mg every 1-2 weeks. Paradoxically, higher doses (30-45mg) are less sedating due to increased noradrenergic tone."
            ),
            commonSideEffects = listOf("Marked sedation / somnolence", "Increased appetite / weight gain", "Dry mouth", "Constipation", "Peripheral edema"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Sedation", "Extreme H1 receptor affinity (Ki 0.14 nM)", "Take at bedtime; educate patient that sedation diminishes at >=30mg due to NE release"),
                SideEffectMechanism("Weight Gain", "Combined H1 and 5-HT2C antagonism in the arcuate nucleus of hypothalamus", "Monitor metabolic profile, fasting glucose, and BMI")
            ),
            blackBoxWarnings = listOf("Suicidality in pediatric and young adult patients."),
            linkedSyndromes = listOf("mdd", "insomnia", "ptsd", "trd"),
            linkedCircuits = listOf("default_mode_network", "hpa_axis", "frontolimbic_circuit"),
            linkedTransmitters = listOf("norepinephrine", "serotonin"),
            clinicalPearls = "A powerful tool for depressed patients with insomnia and cachexia/weight loss (e.g. oncology, elderly). Combines synergistically with Venlafaxine ('California Rocket Fuel') to maximally drive dual NE/5-HT transmission.",
            summary = "Mirtazapine boosts noradrenergic and serotonergic transmission by blocking alpha-2 autoreceptors while antagonising 5-HT2, 5-HT3 and H1 receptors, which explains its anti-nausea effect and sedation. It is the antidepressant of choice when depression presents with insomnia and weight loss, taken at bedtime. Higher doses paradoxically sedate less as noradrenergic drive rises.",
            sources = listOf(Source("CANMAT 2023 MDD guideline (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC11351064/"), Source("DrugBank", "https://go.drugbank.com/"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"))
        ),
        Drug(
            id = "aripiprazole",
            genericName = "Aripiprazole",
            brandName = "Abilify",
            drugClass = "Second-Generation Antipsychotic (D2 Partial Agonist)",
            atcCode = "N05AX12",
            receptorTargets = listOf(
                ReceptorTarget("D2", "Partial Agonist", 0.34, "Very High", "Intrinsic activity ~30%; acts as functional antagonist in hyperdopaminergic states and agonist in hypodopaminergic states"),
                ReceptorTarget("D3", "Partial Agonist", 0.8, "Very High", "Strong limbic affinity; enhances motivation and cognitive processing"),
                ReceptorTarget("5-HT1A", "Partial Agonist", 1.7, "High", "Potent anxiolytic and antidepressant synergy; increases prefrontal cortical DA release"),
                ReceptorTarget("5-HT2A", "Antagonist", 3.4, "High", "Reduces risk of EPS and promotes cortical dopamine transmission"),
                ReceptorTarget("H1", "Antagonist", 61.0, "Moderate", "Low affinity: low risk of sedation and weight gain compared to other SGAs")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "75 hours (active metabolite dehydro-aripiprazole: 94 hours)",
                bioavailability = "87%",
                cypMetabolism = "CYP2D6, CYP3A4 substrate; reduce dose by 50% in CYP2D6 poor metabolizers or when co-administered with fluoxetine/paroxetine",
                timeToPeak = "3.0 - 5.0 hours"
            ),
            dosingRange = DosingRange(
                startingDose = "2 - 5 mg/day (adjunct MDD); 10 - 15 mg/day (Schizo/Bipolar)",
                targetDose = "2 - 10 mg/day (MDD); 15 - 30 mg/day (Psychosis)",
                maxDose = "30 mg/day",
                titrationSchedule = "For MDD adjunct: start 2-5 mg/day, increase by 2-5 mg after 1-2 weeks. For psychosis: start 10-15 mg/day; wait 2 weeks before dose adjustments due to long half-life."
            ),
            commonSideEffects = listOf("Akathisia (motor restlessness)", "Insomnia", "Nausea", "Headache", "Impulse control disorders (gambling/compulsions)"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Akathisia", "Basal ganglia D2 partial agonist intrinsic activity triggering striatal restlessness", "Start low (2mg); treat with Propranolol 10-40mg or low-dose Benzodiazepine"),
                SideEffectMechanism("Impulse Control Disorders", "Mesolimbic D3 partial agonism in reward pathways", "Warn family; screen for pathological gambling or hypersexuality")
            ),
            blackBoxWarnings = listOf("Increased mortality in elderly patients with dementia-related psychosis; Suicidality in children/young adults when used as MDD adjunct."),
            linkedSyndromes = listOf("schizophrenia", "bipolar1", "mdd", "trd", "ocd", "bpd"),
            linkedCircuits = listOf("mesolimbic_pathway", "mesocortical_pathway", "cstc_loop"),
            linkedTransmitters = listOf("dopamine", "serotonin"),
            clinicalPearls = "The 'dopamine stabilizer': partial agonism avoids total D2 shutdown, preserving prolactin and reducing metabolic liability. Low doses (2-5 mg) are gold-standard for antidepressant augmentation in TRD.",
            summary = "Aripiprazole is a D2 partial agonist that dampens dopamine where it is excessive and supports it where deficient, giving antipsychotic efficacy with comparatively low metabolic and prolactin burden. It is used in schizophrenia, bipolar mania, and as augmentation in resistant depression and OCD. Akathisia and impulse-control symptoms are the signature tolerability signals to monitor.",
            sources = listOf(Source("IPS CPG 2025 update (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC12900045"), Source("DrugBank", "https://go.drugbank.com/"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"))
        ),
        Drug(
            id = "quetiapine",
            genericName = "Quetiapine",
            brandName = "Seroquel / Seroquel XR",
            drugClass = "Second-Generation Antipsychotic (MARTA / SDA)",
            atcCode = "N05AH04",
            receptorTargets = listOf(
                ReceptorTarget("H1 Histamine", "Antagonist", 11.0, "High", "Potent sedation and hypnotic effect dominating at low doses (25-50 mg)"),
                ReceptorTarget("5-HT2A", "Antagonist", 38.0, "High", "SGA signature; active at moderate doses (150-300 mg)"),
                ReceptorTarget("Alpha-1", "Antagonist", 22.0, "High", "Orthostatic hypotension and sedation"),
                ReceptorTarget("NET (via Norquetiapine)", "Inhibitor", 12.0, "High", "Active metabolite norquetiapine blocks NET, conferring unique antidepressant efficacy in bipolar depression"),
                ReceptorTarget("D2", "Antagonist", 300.0, "Low", "Transient, fast-off D2 binding ('hit-and-run'); requires high doses (>=600 mg) for antipsychotic efficacy")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "6 hours (parent drug), 12 hours (active metabolite norquetiapine)",
                bioavailability = "9% (moderate due to extensive first-pass metabolism)",
                cypMetabolism = "CYP3A4 (major); avoid concurrent ketoconazole, erythromycin, or carbamazepine",
                timeToPeak = "1.5 hours (IR), 6.0 hours (XR)"
            ),
            dosingRange = DosingRange(
                startingDose = "25 - 50 mg (sleep/anxiety); 50 mg (Bipolar); 150 mg (Psychosis)",
                targetDose = "150 - 300 mg (Bipolar depression); 400 - 800 mg (Mania/Schizophrenia)",
                maxDose = "800 mg/day",
                titrationSchedule = "Bipolar depression: Day 1: 50mg, Day 2: 100mg, Day 3: 200mg, Day 4: 300mg. Schizophrenia: titrate rapidly to 400-800 mg/day."
            ),
            commonSideEffects = listOf("Marked somnolence/sedation", "Weight gain & metabolic syndrome", "Orthostatic dizziness", "Dry mouth", "Constipation"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Metabolic Dysregulation", "Combined H1 + 5-HT2C antagonism with secondary insulin resistance", "Serial fasting glucose, lipid panel, waist circumference"),
                SideEffectMechanism("Orthostatic Hypotension", "Alpha-1 adrenergic antagonism", "Slow positional changes; dose titration at bedtime")
            ),
            blackBoxWarnings = listOf("Increased mortality in elderly patients with dementia-related psychosis; Suicidal thoughts in pediatric/young adults when used as MDD adjunct."),
            linkedSyndromes = listOf("bipolar1", "schizophrenia", "mdd", "trd", "ptsd", "insomnia"),
            linkedCircuits = listOf("mesolimbic_pathway", "salience_network", "default_mode_network"),
            linkedTransmitters = listOf("dopamine", "serotonin", "norepinephrine"),
            clinicalPearls = "A dose-dependent 'chameleon': 25-50 mg = sleeping pill (pure H1); 150-300 mg = antidepressant (norquetiapine NET + 5-HT2A); 600-800 mg = antipsychotic (D2 threshold reached). Negligible EPS or hyperprolactinemia risk.",
            summary = "Quetiapine is a sedating second-generation antipsychotic used across schizophrenia, bipolar depression and mania, and adjunctively in resistant depression, with dose-dependent receptor engagement from histamine blockade at low doses to full D2 antagonism at high doses. Somnolence, dry mouth, weight gain and orthostatic hypotension are typical. Its broad dose range makes indication-specific dosing essential.",
            sources = listOf(Source("IPS CPG 2025 update (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC12900045"), Source("DrugBank", "https://go.drugbank.com/"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"))
        ),
        Drug(
            id = "olanzapine",
            genericName = "Olanzapine",
            brandName = "Zyprexa",
            drugClass = "Second-Generation Antipsychotic (MARTA)",
            atcCode = "N05AH03",
            receptorTargets = listOf(
                ReceptorTarget("5-HT2A", "Antagonist", 4.0, "Very High", "Extreme affinity; exceeds D2 occupancy, providing strong antimanic and antipsychotic stability"),
                ReceptorTarget("D2", "Antagonist", 11.0, "High", "Clean therapeutic window: 70-80% striatal occupancy without extreme EPS threshold"),
                ReceptorTarget("H1", "Antagonist", 7.0, "Very High", "Profound sedation and rapid, marked orexigenic weight gain"),
                ReceptorTarget("M1", "Antagonist", 1.9, "Very High", "Anticholinergic protection against EPS, but risk of constipation and cognitive slowing"),
                ReceptorTarget("5-HT2C", "Antagonist", 11.0, "High", "Synergizes with H1 to trigger intense carbohydrate craving and hypertriglyceridemia")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "21 - 54 hours (mean 30 hours)",
                bioavailability = "85% (not affected by food)",
                cypMetabolism = "CYP1A2 (major substrate); clearance increased ~40% by cigarette smoking (polycyclic aromatic hydrocarbons)",
                timeToPeak = "6.0 hours"
            ),
            dosingRange = DosingRange(
                startingDose = "5 - 10 mg/day (bedtime)",
                targetDose = "10 - 20 mg/day",
                maxDose = "20 mg/day (up to 30 mg in treatment-resistant cases)",
                titrationSchedule = "Start 5-10 mg daily at bedtime. May adjust by 5 mg increments at intervals of not less than 1 week."
            ),
            commonSideEffects = listOf("Severe weight gain (mean 5-10+ kg)", "Hyperglycemia & New-onset Type 2 Diabetes", "Dyslipidemia (marked triglyceride spikes)", "Sedation", "Dry mouth"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Extreme Weight Gain / Diabetes", "H1 and 5-HT2C blockade + direct pancreatic beta-cell muscarinic M3 inhibition", "Baseline & quarterly HbA1c/lipids; co-prescribe Metformin 1000-2000mg defensively"),
                SideEffectMechanism("CYP1A2 Induction by Smoking", "Tar in tobacco smoke induces CYP1A2, dropping blood levels by 40%", "Smoking cessation requires immediate 30-50% dose reduction to avoid toxicity")
            ),
            blackBoxWarnings = listOf("Dementia-related psychosis mortality. Post-injection delirium/sedation syndrome for long-acting injectable (Zyprexa Relprevv)."),
            linkedSyndromes = listOf("schizophrenia", "bipolar1", "trd", "mdd"),
            linkedCircuits = listOf("mesolimbic_pathway", "mesocortical_pathway", "cstc_loop"),
            linkedTransmitters = listOf("dopamine", "serotonin", "acetylcholine"),
            clinicalPearls = "One of the most effective non-clozapine antipsychotics and antimanic agents in psychiatry (CATIE trial). However, devastating metabolic adverse effects necessitate aggressive baseline and ongoing laboratory monitoring.",
            summary = "Olanzapine is a broad-spectrum second-generation antipsychotic with strong efficacy in schizophrenia and bipolar disorder, including rapid control of agitation. Its use is limited mainly by substantial weight gain and metabolic syndrome, mandating baseline and ongoing metabolic monitoring. Potent H1 and 5-HT2C blockade underlie both its calming effect and its metabolic liability.",
            sources = listOf(Source("IPS CPG 2025 update (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC12900045"), Source("DrugBank", "https://go.drugbank.com/"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"))
        ),
        Drug(
            id = "risperidone",
            genericName = "Risperidone",
            brandName = "Risperdal",
            drugClass = "Second-Generation Antipsychotic (SDA)",
            atcCode = "N05AX08",
            receptorTargets = listOf(
                ReceptorTarget("5-HT2A", "Antagonist", 0.5, "Very High", "Sub-nanomolar affinity; blocks 5-HT2A completely before reaching full D2 blockade"),
                ReceptorTarget("D2", "Antagonist", 3.0, "High", "Potent striatal D2 blockade; narrow therapeutic window: doses >4-6 mg/day frequently induce parkinsonian EPS"),
                ReceptorTarget("Alpha-1", "Antagonist", 2.0, "High", "Causes orthostatic hypotension and nasal congestion"),
                ReceptorTarget("Alpha-2", "Antagonist", 8.0, "High", "Presynaptic noradrenergic release enhancement"),
                ReceptorTarget("H1", "Antagonist", 20.0, "Moderate", "Mild-to-moderate sedation and weight gain")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "3 hours (parent drug), 24 hours (active metabolite 9-hydroxyrisperidone / Paliperidone)",
                bioavailability = "70%",
                cypMetabolism = "CYP2D6 converts to active paliperidone; active moiety half-life total ~24 hours",
                timeToPeak = "1.0 hour"
            ),
            dosingRange = DosingRange(
                startingDose = "1 - 2 mg/day (0.5 mg in elderly)",
                targetDose = "2 - 6 mg/day",
                maxDose = "8 mg/day (rarely justified due to EPS above 6 mg)",
                titrationSchedule = "Start 1-2 mg/day; titrate by 1 mg/day every 24-48 hours. Most patients achieve complete D2 response at 2-4 mg/day."
            ),
            commonSideEffects = listOf("Hyperprolactinemia (galactorrhea, amenorrhea, gynecomastia)", "Dose-dependent EPS (parkinsonism, tremor, rigidity)", "Weight gain", "Orthostatic dizziness", "Sedation"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Hyperprolactinemia", "Complete D2 blockade in pituitary lactotrophs (outside blood-brain barrier)", "Check serum prolactin if sexual dysfunction/amenorrhea; switch to Aripiprazole if elevated"),
                SideEffectMechanism("EPS / Tremor", "Striatal D2 occupancy exceeding 80% at doses >=4-6mg", "Reduce dose to <=4mg; add Trihexyphenidyl or switch agent")
            ),
            blackBoxWarnings = listOf("Increased mortality in elderly patients with dementia-related psychosis."),
            linkedSyndromes = listOf("schizophrenia", "bipolar1", "ocd"),
            linkedCircuits = listOf("mesolimbic_pathway", "cstc_loop"),
            linkedTransmitters = listOf("dopamine", "serotonin"),
            clinicalPearls = "The quintessential serotonin-dopamine antagonist (SDA). Be careful not to dose like a typical antipsychotic: 2-4 mg is the sweet spot. Highest incidence of prolactin elevation among all SGAs.",
            summary = "Risperidone is a potent D2 and 5-HT2A antagonist effective in schizophrenia, bipolar mania and irritability in autism, available in long-acting injectable form. Dose-dependent prolactin elevation, weight gain and extrapyramidal symptoms track its high D2 occupancy. It remains a benchmark comparator in antipsychotic trials.",
            sources = listOf(Source("IPS CPG 2025 update (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC12900045"), Source("DrugBank", "https://go.drugbank.com/"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"))
        ),
        Drug(
            id = "clozapine",
            genericName = "Clozapine",
            brandName = "Clozaril",
            drugClass = "Second-Generation Antipsychotic (Gold-Standard Atypical)",
            atcCode = "N05AH02",
            receptorTargets = listOf(
                ReceptorTarget("D4", "Antagonist", 9.0, "High", "High selectivity for limbic D4 over striatal D2"),
                ReceptorTarget("D2", "Antagonist", 125.0, "Low", "Transient, low-affinity 'fast-off' binding (30-60% occupancy); virtually never causes EPS or tardive dyskinesia"),
                ReceptorTarget("5-HT2A", "Antagonist", 5.0, "Very High", "Strong serotonin antagonism balancing dopamine tone"),
                ReceptorTarget("M1/M4", "Partial Agonist / Antagonist", 1.9, "Very High", "Complex muscarinic activity; paradoxically causes severe nocturnal hypersalivation (sialorrhea)"),
                ReceptorTarget("H1", "Antagonist", 6.0, "Very High", "Profound sedation and metabolic weight gain"),
                ReceptorTarget("Alpha-1", "Antagonist", 7.0, "Very High", "Significant orthostatic hypotension and syncope risk")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "12 - 16 hours",
                bioavailability = "50-60%",
                cypMetabolism = "CYP1A2 (major), CYP3A4, CYP2C19. Cigarette smoking dramatically lowers blood levels via CYP1A2 induction",
                timeToPeak = "1.5 - 2.5 hours"
            ),
            dosingRange = DosingRange(
                startingDose = "12.5 mg once or twice daily",
                targetDose = "300 - 450 mg/day (therapeutic blood level >=350 ng/mL)",
                maxDose = "900 mg/day",
                titrationSchedule = "Mandatory slow titration: Day 1: 12.5mg, Day 2: 25mg, then increase by 25-50mg/day as tolerated up to 300mg by week 2-3 to prevent cardiovascular collapse and seizures."
            ),
            commonSideEffects = listOf("Severe nocturnal sialorrhea (drooling)", "Sedation", "Severe constipation / paralytic ileus", "Weight gain / metabolic syndrome", "Tachycardia & orthostasis"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Agranulocytosis / Severe Neutropenia", "Immune-mediated toxic oxidation of nitrenium ions leading to neutrophil apoptosis", "Mandatory REMS absolute neutrophil count (ANC) tracking: weekly for 6mo, biweekly for 6mo, then monthly"),
                SideEffectMechanism("Myocarditis / Cardiomyopathy", "IgE-mediated eosinophilic hypersensitivity myocardial necrosis", "Baseline ECG, troponin, and weekly troponin/CRP during first 4 weeks"),
                SideEffectMechanism("Paralytic Gastrointestinal Ileus", "Potent peripheral and central anticholinergic M1/M3 blockade", "Rule out constipation proactively; fatal bowel necrosis can occur without pain")
            ),
            blackBoxWarnings = listOf("Severe neutropenia (agranulocytosis), Orthostatic hypotension/syncope, Seizures (dose-dependent), Myocarditis and cardiomyopathy, Dementia mortality."),
            linkedSyndromes = listOf("schizophrenia", "bipolar1", "trd"),
            linkedCircuits = listOf("mesolimbic_pathway", "mesocortical_pathway"),
            linkedTransmitters = listOf("dopamine", "serotonin", "acetylcholine"),
            clinicalPearls = "The single most effective antipsychotic in existence; the only drug FDA-approved for treatment-resistant schizophrenia and reducing suicide in schizophrenia. Never given first-line due to agranulocytosis monitoring mandates.",
            summary = "Clozapine is the only antipsychotic with proven superiority in treatment-resistant schizophrenia and in reducing suicidality, acting through uniquely broad receptor binding with relatively low D2 occupancy. Mandatory blood monitoring manages the agranulocytosis risk, alongside vigilance for myocarditis, seizures and severe constipation. Its benefits outweigh burdens only where two prior antipsychotics have failed.",
            sources = listOf(Source("IPS CPG 2025 update (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC12900045"), Source("DrugBank", "https://go.drugbank.com/"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"))
        ),
        Drug(
            id = "lithium",
            genericName = "Lithium Carbonate",
            brandName = "Lithobid / Eskalith",
            drugClass = "Mood Stabilizer (Monovalent Cation)",
            atcCode = "N05AN01",
            receptorTargets = listOf(
                ReceptorTarget("GSK-3beta", "Inhibitor", null, "High", "Direct and indirect inhibition of glycogen synthase kinase 3beta; promotes neurogenesis, synaptogenesis, and neuroprotection"),
                ReceptorTarget("Inositol Monophosphatase (IMPase)", "Inhibitor", null, "High", "'Inositol depletion hypothesis': shuts down hyperactive Gq-coupled phosphoinositide (IP3/DAG) second messenger cascades"),
                ReceptorTarget("NMDA Receptor", "Modulator", null, "Moderate", "Attenuates glutamate-mediated excitotoxicity and downregulates NMDA receptor surface density")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "18 - 24 hours (prolonged in renal impairment and elderly)",
                bioavailability = "100%",
                cypMetabolism = "Not metabolized by liver; excreted 100% unchanged through renal glomerular filtration (shares proximal tubule reabsorption with Sodium)",
                timeToPeak = "0.5 - 3.0 hours (IR), 4.0 - 12.0 hours (ER)"
            ),
            dosingRange = DosingRange(
                startingDose = "300 mg twice daily (or 600 mg QHS)",
                targetDose = "900 - 1200 mg/day (Target level: Acute mania 0.8-1.2 mEq/L; Maintenance 0.6-0.8 mEq/L)",
                maxDose = "Titrate to 12-hour trough serum level (toxicity starts >1.2 mEq/L, severe >2.0 mEq/L)",
                titrationSchedule = "Check 12-hour trough level 5-7 days after initiation and after every dose change until steady state. Once stable, monitor every 3-6 months alongside renal and thyroid panels."
            ),
            commonSideEffects = listOf("Fine hand tremor", "Polyuria & polydipsia (nephrogenic DI)", "Hypothyroidism & goiter", "Nausea/vomiting", "Weight gain", "Acne/psoriasis exacerbation"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Nephrogenic Diabetes Insipidus", "Interferes with vasopressin (ADH)-mediated V2 aquaporin-2 channel insertion in renal collecting duct", "Hydration; amiloride if severe polyuria; check eGFR and creatinine annually"),
                SideEffectMechanism("Hypothyroidism", "Inhibits thyroid hormone iodination and release from thyroid follicles", "Check baseline and biannual TSH/free T4; treat with Levothyroxine without discontinuing Lithium"),
                SideEffectMechanism("Toxicity with Dehydration/NSAIDs/ACEi/Diuretics", "Sodium depletion causes proximal tubule to hyper-reabsorb lithium ion", "Avoid NSAIDs, thiazides, and ACE inhibitors; maintain steady salt and fluid intake")
            ),
            blackBoxWarnings = listOf("Lithium toxicity is closely related to serum levels and can occur at doses close to therapeutic concentrations (narrow therapeutic index)."),
            linkedSyndromes = listOf("bipolar1", "mdd", "trd"),
            linkedCircuits = listOf("mesolimbic_pathway", "frontolimbic_circuit", "hpa_axis"),
            linkedTransmitters = listOf("glutamate", "dopamine"),
            clinicalPearls = "The benchmark mood stabilizer for Bipolar I disorder with proven anti-suicidal properties independent of mood state. Always check levels exactly 12 hours post-dose (trough level).",
            summary = "Lithium remains the gold-standard mood stabiliser for bipolar prophylaxis, with unique anti-suicidal effects, acting through inositol and GSK-3 signalling rather than a single receptor. Its narrow therapeutic index demands serum monitoring and attention to renal, thyroid and hydration status. Even low-dose augmentation benefits resistant depression.",
            sources = listOf(Source("IPS CPG 2025 update (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC12900045"), Source("CANMAT guidelines", "https://www.canmat.org/"), Source("DrugBank", "https://go.drugbank.com/"))
        ),
        Drug(
            id = "lamotrigine",
            genericName = "Lamotrigine",
            brandName = "Lamictal",
            drugClass = "Mood Stabilizer / Anticonvulsant",
            atcCode = "N03AX09",
            receptorTargets = listOf(
                ReceptorTarget("Voltage-Gated Na+ Channels", "Blocker", null, "High", "Use-dependent blockade of voltage-sensitive sodium channels on cortical and hippocampal pyramidal neurons"),
                ReceptorTarget("Presynaptic Glutamate Release", "Inhibitor", null, "High", "Stabilizes neuronal membranes, decreasing hyperactive glutamate and aspartate efflux in limbic pathways"),
                ReceptorTarget("N- and P-Type Ca2+ Channels", "Weak Blocker", null, "Moderate", "Attenuates high-voltage activated calcium influx in presynaptic terminals")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "25 - 30 hours (doubled to ~60h by Valproate; halved to ~15h by Carbamazepine/OCPs)",
                bioavailability = "98%",
                cypMetabolism = "Hepatic glucuronidation via UGT1A4; clearance increased up to 50% by estrogen-containing oral contraceptives",
                timeToPeak = "1.5 - 4.8 hours"
            ),
            dosingRange = DosingRange(
                startingDose = "25 mg/day (Weeks 1-2)",
                targetDose = "100 - 200 mg/day (Maintenance)",
                maxDose = "200 mg/day (Bipolar); 400 mg/day (Epilepsy)",
                titrationSchedule = "Mandatory slow escalation: Wks 1-2: 25mg/day; Wks 3-4: 50mg/day; Wk 5: 100mg/day; Wk 6: 200mg/day. If co-administered with Valproate, start 25mg EVERY OTHER DAY."
            ),
            commonSideEffects = listOf("Benign rash", "Headache", "Dizziness / ataxia", "Nausea", "Diplopia / blurred vision"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Stevens-Johnson Syndrome (SJS) / TEN", "Immunologically mediated toxic epidermal necrolysis tied to rapid dose titration", "Adhere strictly to titration schedule; stop immediately at any sign of rash involving mucous membranes or fever"),
                SideEffectMechanism("Interaction with Oral Contraceptives", "Estrogen induces UGT1A4 glucuronidation, halving lamotrigine serum levels", "Double dose during active pill days, reduce during placebo week to prevent toxicity")
            ),
            blackBoxWarnings = listOf("Serious, potentially life-threatening rashes including Stevens-Johnson Syndrome (SJS) and Toxic Epidermal Necrolysis (TEN)."),
            linkedSyndromes = listOf("bipolar1", "bpd", "mdd", "trd"),
            linkedCircuits = listOf("frontolimbic_circuit", "cstc_loop"),
            linkedTransmitters = listOf("glutamate", "gaba"),
            clinicalPearls = "First-line for preventing Bipolar depression and rapid cycling without triggering manic switches. Excellent tolerability (weight neutral, non-sedating), but slow 6-week titration is non-negotiable.",
            summary = "Lamotrigine is a glutamate-release inhibitor most valuable for preventing bipolar depressive relapse rather than treating acute mania. A slow six-week titration is mandatory to limit rash risk, after which tolerability is among the best in class. Valproate doubles its levels while enzyme inducers halve them.",
            sources = listOf(Source("IPS CPG 2025 update (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC12900045"), Source("CANMAT guidelines", "https://www.canmat.org/"), Source("DrugBank", "https://go.drugbank.com/"))
        ),
        Drug(
            id = "divalproex",
            genericName = "Divalproex / Valproic Acid",
            brandName = "Depakote / Depakene",
            drugClass = "Mood Stabilizer / Anticonvulsant",
            atcCode = "N03AG01",
            receptorTargets = listOf(
                ReceptorTarget("GABA Transaminase (GABA-T)", "Inhibitor", null, "High", "Inhibits GABA breakdown and stimulates GAD, augmenting central GABAergic inhibitory tone"),
                ReceptorTarget("Voltage-Gated Na+ Channels", "Blocker", null, "High", "Prolongs recovery from inactivation, dampening high-frequency repetitive firing in limbic loops"),
                ReceptorTarget("T-type Ca2+ Channels", "Blocker", null, "Moderate", "Attenuates thalamocortical oscillatory burst firing")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "9 - 16 hours",
                bioavailability = "90%",
                cypMetabolism = "Extensive hepatic glucuronidation (50%) and mitochondrial beta-oxidation (40%); potent inhibitor of CYP2C9 and UGT1A4 (doubles lamotrigine levels)",
                timeToPeak = "3.0 - 5.0 hours (delayed release)"
            ),
            dosingRange = DosingRange(
                startingDose = "250 - 500 mg twice daily (or loading dose 20-25 mg/kg for acute mania)",
                targetDose = "1000 - 2000 mg/day (Serum therapeutic level: 50 - 125 mcg/mL)",
                maxDose = "Titrate to serum level (toxic threshold >125 mcg/mL)",
                titrationSchedule = "Acute mania: oral loading at 20-25 mg/kg/day gives rapid symptom control in 3-5 days. Check 12-hour trough level after 3-5 days."
            ),
            commonSideEffects = listOf("Nausea/vomiting", "Alopecia (hair thinning)", "Significant weight gain", "Fine tremor", "Sedation"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Hepatotoxicity & Pancreatitis", "Toxic metabolite 4-ene-VPA causes mitochondrial dysfunction and oxidative necrosis", "Baseline and periodic LFTs, amylase/lipase if acute abdominal pain arises"),
                SideEffectMechanism("Hyperammonemic Encephalopathy", "Inhibition of carbamoyl phosphate synthetase I in the urea cycle", "Check serum ammonia in acute confusion/lethargy even if LFTs are normal; treat with L-carnitine"),
                SideEffectMechanism("Teratogenicity (Neural Tube Defects)", "Inhibition of histone deacetylases (HDAC) and folate metabolism", "Major congenital malformations (spina bifida ~1-2%), facial dysmorphisms, and IQ deficits; avoid in women of childbearing potential")
            ),
            blackBoxWarnings = listOf("Fatal hepatic failure, Pancreatitis, and Severe teratogenicity (neural tube defects, craniofacial defects, cognitive impairment)."),
            linkedSyndromes = listOf("bipolar1", "aud"),
            linkedCircuits = listOf("cstc_loop", "frontolimbic_circuit"),
            linkedTransmitters = listOf("gaba", "glutamate"),
            clinicalPearls = "Gold standard for acute manic excitement, mixed episodes, and rapid cycling. Oral loading (20 mg/kg) controls acute mania faster than lithium. Strongly avoid in females of childbearing age due to severe teratogenicity and PCOS risk.",
            summary = "Divalproex (valproate) controls acute mania and mixed states through GABA potentiation and histone-deacetylase effects, with loading-dose strategies for rapid stabilisation. Teratogenicity rules it out in pregnancy, and hepatic, haematologic and weight monitoring is routine. It is also a first-line migraine prophylactic.",
            sources = listOf(Source("IPS CPG 2025 update (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC12900045"), Source("DrugBank", "https://go.drugbank.com/"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"))
        ),
        Drug(
            id = "clonazepam",
            genericName = "Clonazepam",
            brandName = "Klonopin",
            drugClass = "Benzodiazepine (High-Potency)",
            atcCode = "N03AE01",
            receptorTargets = listOf(
                ReceptorTarget("GABA-A Receptor (alpha1, alpha2, alpha3, alpha5 subunits)", "Positive Allosteric Modulator (PAM)", 0.5, "Very High", "Binds the BZD allosteric pocket between alpha and gamma subunits, increasing channel opening frequency to Cl- ions and producing rapid hyperpolarization")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "30 - 40 hours",
                bioavailability = "90%",
                cypMetabolism = "CYP3A4 hepatic nitroreduction to inactive 7-amino-clonazepam",
                timeToPeak = "1.0 - 4.0 hours"
            ),
            dosingRange = DosingRange(
                startingDose = "0.25 - 0.5 mg twice daily",
                targetDose = "1.0 - 2.0 mg/day",
                maxDose = "4.0 mg/day (panic disorder)",
                titrationSchedule = "Start 0.25-0.5 mg BID; titrate by 0.5 mg every 3 days toward symptom control. Plan taper duration equivalent to at least length of active use."
            ),
            commonSideEffects = listOf("Sedation / drowsiness", "Ataxia / psychomotor impairment", "Anterograde amnesia", "Tolerance and physiological dependence", "Respiratory depression (synergistic with opioids)"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Tolerance & Dependence", "Downregulation and uncoupling of GABA-A receptor alpha-1/alpha-2 subunits from G-protein signaling", "Short-term bridge use only (2-4 weeks); taper by <=10% per week to avoid seizures and rebound panic"),
                SideEffectMechanism("Fatal Synergistic Respiratory Depression", "Simultaneous depression of medullary respiratory drive when combined with Opioids or Alcohol", "Never combine with high-dose opioids or alcohol; co-prescribe Naloxone in at-risk patients")
            ),
            blackBoxWarnings = listOf("Concomitant use of benzodiazepines and opioids may result in profound sedation, respiratory depression, coma, and death. Physical dependence and withdrawal."),
            linkedSyndromes = listOf("panic_disorder", "gad", "insomnia", "bipolar1"),
            linkedCircuits = listOf("frontolimbic_circuit", "salience_network"),
            linkedTransmitters = listOf("gaba"),
            clinicalPearls = "Long half-life (30-40h) makes it superior to Alprazolam (Xanax) by avoiding inter-dose rebound anxiety and intense addictive peaks. Excellent 2-4 week bridge while awaiting SSRI onset.",
            summary = "Clonazepam is a long-acting benzodiazepine that rapidly damps panic, acute mania-related agitation and catatonia via GABA-A potentiation. Dependence, withdrawal seizures and cognitive dulling restrict it to short-term or carefully supervised maintenance use with a taper plan. It is not a substitute for definitive SSRI or mood-stabiliser therapy.",
            sources = listOf(Source("NICE mental health guidance index", "https://www.nice.org.uk/guidance/conditions-and-diseases/mental-health-behavioural-and-neurodevelopmental-conditions"), Source("DrugBank", "https://go.drugbank.com/"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"))
        ),
        Drug(
            id = "buspirone",
            genericName = "Buspirone",
            brandName = "Buspar",
            drugClass = "Azapirone Anxiolytic (5-HT1A Partial Agonist)",
            atcCode = "N05BE01",
            receptorTargets = listOf(
                ReceptorTarget("5-HT1A", "Partial Agonist", 15.0, "High", "High intrinsic activity at somatodendritic autoreceptors in raphe; partial agonist at cortical post-synaptic sites"),
                ReceptorTarget("D2", "Antagonist / Presynaptic Modulator", 150.0, "Moderate", "Weak dopamine D2 antagonist; may increase prefrontal dopamine and norepinephrine release"),
                ReceptorTarget("Alpha-1", "Weak Ligand", 400.0, "Low", "Negligible clinical sedation or hypotension")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "2 - 3 hours (short parent half-life)",
                bioavailability = "4% (extensive first-pass metabolism; increased threefold by food)",
                cypMetabolism = "CYP3A4 substrate; levels dramatically increased by grapefruit juice, verapamil, erythromycin",
                timeToPeak = "0.75 - 1.5 hours"
            ),
            dosingRange = DosingRange(
                startingDose = "7.5 mg twice daily (or 5 mg TID)",
                targetDose = "20 - 30 mg/day (divided BID or TID)",
                maxDose = "60 mg/day",
                titrationSchedule = "Start 7.5 mg BID. Increase by 5 mg/day every 2-3 days up to 30 mg/day in divided doses. Requires 2-4 weeks for therapeutic anxiolysis."
            ),
            commonSideEffects = listOf("Dizziness / lightheadedness", "Nausea", "Headache", "Nervousness / excitement", "Restlessness"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Lack of Sedation or Addiction", "Completely devoid of GABA-A receptor affinity", "Zero abuse potential, no motor impairment, no withdrawal syndrome"),
                SideEffectMechanism("Delayed Efficacy", "Requires 5-HT1A autoreceptor desensitization similar to SSRIs", "Educate patient that it cannot be taken PRN; must be taken consistently daily")
            ),
            blackBoxWarnings = listOf("None (one of the safest anxiolytics in psychopharmacology)."),
            linkedSyndromes = listOf("gad", "mdd"),
            linkedCircuits = listOf("frontolimbic_circuit"),
            linkedTransmitters = listOf("serotonin", "dopamine"),
            clinicalPearls = "Not a PRN anxiolytic: must be taken scheduled BID/TID. Ineffective for acute panic attacks. Excellent non-addictive maintenance option for GAD and augmenting SSRIs to alleviate SSRI-induced sexual dysfunction.",
            summary = "Buspirone is a 5-HT1A partial agonist for generalised anxiety that avoids the sedation, dependence and withdrawal of benzodiazepines. Its one-to-two-week onset lag and twice-daily dosing require expectation-setting, and it works best in benzodiazepine-naive patients. It also modestly augments SSRIs in resistant depression.",
            sources = listOf(Source("NICE mental health guidance index", "https://www.nice.org.uk/guidance/conditions-and-diseases/mental-health-behavioural-and-neurodevelopmental-conditions"), Source("DrugBank", "https://go.drugbank.com/"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"))
        ),
        Drug(
            id = "methylphenidate",
            genericName = "Methylphenidate",
            brandName = "Ritalin / Concerta",
            drugClass = "Central Nervous System Stimulant (NDRI)",
            atcCode = "N06BA04",
            receptorTargets = listOf(
                ReceptorTarget("DAT", "Inhibitor", 60.0, "High", "Allosterically blocks the dopamine transporter, preventing reuptake and elevating extracellular dopamine in the striatum and PFC"),
                ReceptorTarget("NET", "Inhibitor", 150.0, "High", "Blocks norepinephrine reuptake transporter in the prefrontal cortex, enhancing signal gain"),
                ReceptorTarget("5-HT1A", "Agonist", null, "Low", "Trace affinity with minor physiological impact")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "2 - 3 hours (extended OROS Concerta delivers biphasic release over 10-12 hours)",
                bioavailability = "30% (d-threo-isomer active)",
                cypMetabolism = "De-esterified by hepatic carboxylesterase CES1A1 to inactive ritalinic acid (independent of CYP enzymes)",
                timeToPeak = "1-2 hours (IR), 6-8 hours (OROS)"
            ),
            dosingRange = DosingRange(
                startingDose = "5 - 10 mg BID (IR) or 18 mg QAM (Concerta)",
                targetDose = "20 - 40 mg/day (IR) or 36 - 54 mg/day (Concerta)",
                maxDose = "60 mg/day (IR) or 72 mg/day (Concerta)",
                titrationSchedule = "Start 18 mg Concerta in morning; titrate by 18 mg weekly based on objective academic/occupational attention logs."
            ),
            commonSideEffects = listOf("Appetite suppression & weight loss", "Insomnia", "Tachycardia & elevated systolic blood pressure", "Anxiety", "Rebound irritability upon wearing off"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Cardiovascular Activation", "Peripheral noradrenergic beta-1 cardiac and alpha-1 vascular tone", "Monitor pulse and BP baseline and every follow-up; ECG if cardiac history"),
                SideEffectMechanism("Growth Velocity Attenuation", "Suppression of appetite and sleep alterations in developing children", "Drug holidays on weekends/summers; track growth percentiles")
            ),
            blackBoxWarnings = listOf("High potential for abuse and dependence. Assess risk of abuse prior to prescribing and monitor for signs of misuse."),
            linkedSyndromes = listOf("adhd", "mdd"),
            linkedCircuits = listOf("mesocortical_pathway", "cstc_loop"),
            linkedTransmitters = listOf("dopamine", "norepinephrine"),
            clinicalPearls = "Unlike amphetamines (which reverse DAT/VMAT2 to dump dopamine), methylphenidate is a pure reuptake blocker, making it less prone to neurotoxic vesicular depletion and tachycardia.",
            summary = "Methylphenidate blocks dopamine and norepinephrine reuptake, making it first-line for ADHD across ages in immediate- and extended-release forms. Appetite suppression, insomnia and small blood-pressure rises are the routine monitoring points, with misuse liability managed through supervised prescribing. Cardiovascular screening precedes initiation.",
            sources = listOf(Source("NICE mental health guidance index", "https://www.nice.org.uk/guidance/conditions-and-diseases/mental-health-behavioural-and-neurodevelopmental-conditions"), Source("DrugBank", "https://go.drugbank.com/"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"))
        ),
        Drug(
            id = "esketamine",
            genericName = "Esketamine / Ketamine",
            brandName = "Spravato",
            drugClass = "Glutamate NMDA Receptor Antagonist (Rapid-Acting Antidepressant)",
            atcCode = "N06AX27",
            receptorTargets = listOf(
                ReceptorTarget("NMDA Receptor (PCP site)", "Non-Competitive Antagonist", 300.0, "High", "Blocks open NMDA channel pore on GABAergic cortical interneurons, disinhibiting pyramidal glutamate bursts"),
                ReceptorTarget("AMPA Receptor", "Indirect Activator", null, "High", "Surge in synaptic glutamate activates AMPA receptors, triggering voltage-gated Ca2+ channels and BDNF exocytosis"),
                ReceptorTarget("mTORC1 Pathway", "Intracellular Activator", null, "High", "Phosphorylates p70S6K and 4E-BP1, inducing rapid dendritic spine formation in prefrontal cortex within hours"),
                ReceptorTarget("Mu Opioid Receptor", "Low-Affinity Agonist", 11000.0, "Low", "Minor contributor; opioid blockade with naltrexone attenuates but does not eliminate acute antidepressant effect")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "7 - 12 hours",
                bioavailability = "48% (intranasal formulation)",
                cypMetabolism = "CYP2B6, CYP3A4 to active noresketamine",
                timeToPeak = "20 - 40 minutes"
            ),
            dosingRange = DosingRange(
                startingDose = "56 mg intranasal (Day 1)",
                targetDose = "56 mg or 84 mg twice weekly for 4 weeks",
                maxDose = "84 mg per session",
                titrationSchedule = "Induction: Twice weekly for 4 weeks. Maintenance: Weekly for weeks 5-8, then every 1-2 weeks. Must be administered under direct healthcare observation with 2-hour monitoring."
            ),
            commonSideEffects = listOf("Dissociation / derealization", "Transient blood pressure surge", "Dizziness / vertigo", "Nausea / vomiting", "Hypoesthesia / sedation"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Transient Dissociation", "Cortical-subcortical desynchronization from selective NMDA blockade", "Self-resolves in 60-90 minutes; calm, dark room with psychological grounding"),
                SideEffectMechanism("Acute Systolic/Diastolic BP Spikes", "Centrally mediated sympathetic outflow", "Monitor BP at baseline, 40 min, and before discharge; do not administer if baseline BP >140/90")
            ),
            blackBoxWarnings = listOf("Risk for sedation and dissociation, Abuse and misuse, REMS requirement, Suicidal thoughts and behaviors in young adults."),
            linkedSyndromes = listOf("trd", "mdd"),
            linkedCircuits = listOf("default_mode_network", "hpa_axis", "frontolimbic_circuit"),
            linkedTransmitters = listOf("glutamate"),
            clinicalPearls = "The first fundamentally novel mechanism antidepressant in 50 years. Generates rapid synaptogenesis and eliminates acute suicidal ideation within hours, contrasting with the 4-6 week lag of monoaminergic drugs.",
            summary = "Esketamine nasal spray is an NMDA antagonist producing rapid improvement in treatment-resistant depression through glutamate-driven synaptic plasticity. Administration is restricted to supervised settings with post-dose monitoring for dissociation and blood-pressure spikes. It complements rather than replaces ongoing oral antidepressants.",
            sources = listOf(Source("CANMAT 2023 MDD guideline (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC11351064/"), Source("NICE mental health guidance index", "https://www.nice.org.uk/guidance/conditions-and-diseases/mental-health-behavioural-and-neurodevelopmental-conditions"), Source("DrugBank", "https://go.drugbank.com/"))
        ),
        Drug(
            id = "clomipramine",
            genericName = "Clomipramine",
            brandName = "Anafranil",
            drugClass = "Tricyclic Antidepressant (TCA)",
            atcCode = "N06AA04",
            receptorTargets = listOf(
                ReceptorTarget("SERT (5-HTT)", "Inhibitor", null, "High", "Most potent serotonin-reuptake blockade among TCAs; basis of the anti-obsessional effect"),
                ReceptorTarget("NET", "Inhibitor", null, "Moderate", "Norepinephrine reuptake blockade via parent drug and active desmethyl metabolite"),
                ReceptorTarget("M1 Muscarinic", "Antagonist", null, "High", "Dry mouth, constipation, urinary retention, blurred vision and cognitive dulling"),
                ReceptorTarget("H1 Histamine", "Antagonist", null, "High", "Sedation and weight gain"),
                ReceptorTarget("Alpha-1 Adrenergic", "Antagonist", null, "Moderate", "Orthostatic hypotension and reflex tachycardia")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "20-40 hours (active desmethyl metabolite longer)",
                bioavailability = "About 50% (extensive first-pass metabolism)",
                cypMetabolism = "CYP2D6 and CYP2C19 substrate; poor metabolisers accumulate parent drug",
                timeToPeak = "2 to 6 hours"
            ),
            dosingRange = DosingRange(
                startingDose = "25 mg/day (usually at bedtime)",
                targetDose = "100-150 mg/day",
                maxDose = "250 mg/day in OCD with plasma monitoring",
                titrationSchedule = "Start 25 mg daily; increase by 25 mg every few days as tolerated. ECG and postural vitals in older adults."
            ),
            commonSideEffects = listOf("Anticholinergic effects (dry mouth, constipation, blurred vision)", "Sedation and fatigue", "Weight gain", "Orthostatic hypotension", "QT prolongation", "Sweating and tremor"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Anticholinergic cluster", "M1 muscarinic antagonism", "Dose reduction, divided dosing; avoid in urinary retention, glaucoma, ileus"),
                SideEffectMechanism("Orthostatic hypotension", "Alpha-1 adrenergic blockade", "Bedtime dosing, hydration, slow posture changes"),
                SideEffectMechanism("Cardiac conduction slowing", "Cardiac sodium-channel blockade in overdose", "Baseline ECG; treat any overdose as a medical emergency")
            ),
            blackBoxWarnings = listOf("Suicidality in children, adolescents and young adults during early treatment.", "Lethal in overdose: narrow therapeutic index; limit quantities in suicidal patients."),
            linkedSyndromes = listOf("ocd", "mdd", "panic_disorder", "trd"),
            linkedCircuits = listOf("cstc_loop", "frontolimbic_circuit"),
            linkedTransmitters = listOf("serotonin", "norepinephrine"),
            clinicalPearls = "The most serotonergic tricyclic and a benchmark anti-obsessional; reserved for SSRI-resistant OCD and selected melancholic depressions because anticholinergic, cardiac and overdose toxicity demand respect.",
            summary = "Clomipramine is a tricyclic antidepressant and the most potent serotonin-reuptake blocker of its class, making it a reference treatment for obsessive-compulsive disorder after SSRI trials. Its broad receptor binding brings anticholinergic, sedative, hypotensive and pro-arrhythmic effects that require ECG and tolerability monitoring. A narrow therapeutic index and lethality in overdose mean quantities are limited in patients with suicidal ideation.",
            sources = listOf(Source("IPS CPG 2025 update (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC12900045"), Source("DrugBank", "https://go.drugbank.com/"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"))
        ),
        Drug(
            id = "fluoxetine",
            genericName = "Fluoxetine",
            brandName = "Prozac",
            drugClass = "Selective Serotonin Reuptake Inhibitor (SSRI)",
            atcCode = "N06AB03",
            receptorTargets = listOf(
                ReceptorTarget("SERT (5-HTT)", "Inhibitor", null, "High", "Sustained serotonin reuptake blockade; longest half-life of the class"),
                ReceptorTarget("5-HT2C", "Antagonist", null, "Moderate", "Contributes to activating profile and relative weight neutrality")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "4-6 days (active norfluoxetine 7-15 days)",
                bioavailability = "Well absorbed orally; long half-life dominates kinetics",
                cypMetabolism = "Potent inhibitor of CYP2D6; substrate of CYP2D6 and CYP2C9",
                timeToPeak = "6 to 8 hours"
            ),
            dosingRange = DosingRange(
                startingDose = "20 mg/day",
                targetDose = "20-40 mg/day",
                maxDose = "80 mg/day",
                titrationSchedule = "Start 20 mg daily; increase after several weeks if needed. Self-tapering on discontinuation due to the long half-life."
            ),
            commonSideEffects = listOf("Nausea", "Insomnia and activation", "Sexual dysfunction", "Tremor", "Hyponatremia in older adults"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Gastrointestinal upset", "Enteric 5-HT3 stimulation", "Take with food; transient over 1-2 weeks"),
                SideEffectMechanism("Sexual dysfunction", "5-HT2A-mediated spinal reflex inhibition", "Dose timing strategies or add bupropion"),
                SideEffectMechanism("Drug interactions", "Potent CYP2D6 inhibition", "Review all co-medication; doubles levels of many 2D6 substrates")
            ),
            blackBoxWarnings = listOf("Suicidality in children, adolescents and young adults during early treatment."),
            linkedSyndromes = listOf("mdd", "ocd", "panic_disorder"),
            linkedCircuits = listOf("frontolimbic_circuit", "default_mode_network", "cstc_loop"),
            linkedTransmitters = listOf("serotonin"),
            clinicalPearls = "The original SSRI with a week-long half-life that forgives missed doses and self-tapers; potent CYP2D6 inhibition complicates polypharmacy.",
            summary = "Fluoxetine was the first SSRI and remains a first-line antidepressant with additional indications in OCD, panic disorder and bulimia. Its exceptionally long half-life smooths missed doses and discontinuation but prolongs drug interactions and washout before MAOI switches. Potent CYP2D6 inhibition demands a full medication review in medically complex patients.",
            sources = listOf(Source("CANMAT 2023 MDD guideline (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC11351064/"), Source("DrugBank", "https://go.drugbank.com/"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"))
        ),
        Drug(
            id = "duloxetine",
            genericName = "Duloxetine",
            brandName = "Cymbalta",
            drugClass = "Serotonin-Norepinephrine Reuptake Inhibitor (SNRI)",
            atcCode = "N06AX21",
            receptorTargets = listOf(
                ReceptorTarget("SERT", "Inhibitor", null, "High", "Serotonin reuptake blockade across the dose range"),
                ReceptorTarget("NET", "Inhibitor", null, "Moderate", "Balanced noradrenergic recruitment supporting pain pathways")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "About 12 hours",
                bioavailability = "About 50% (enteric-coated, acid-labile)",
                cypMetabolism = "CYP1A2 and CYP2D6 substrate; moderate CYP2D6 inhibitor",
                timeToPeak = "About 6 hours"
            ),
            dosingRange = DosingRange(
                startingDose = "30 mg/day",
                targetDose = "60 mg/day",
                maxDose = "120 mg/day",
                titrationSchedule = "Start 30 mg daily for one week, then 60 mg daily. Taper slowly; discontinuation symptoms are prominent."
            ),
            commonSideEffects = listOf("Nausea (early, often transient)", "Dry mouth and constipation", "Dizziness", "Mild blood pressure rise", "Urinary hesitation", "Discontinuation syndrome"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Early nausea", "Enteric 5-HT3 stimulation", "Start at 30 mg; take with food if needed"),
                SideEffectMechanism("Blood pressure rise", "Noradrenergic vascular tone", "Monitor BP; use caution in uncontrolled hypertension"),
                SideEffectMechanism("Discontinuation syndrome", "Short half-life with dual-mechanism withdrawal", "Taper over weeks, never stop abruptly")
            ),
            blackBoxWarnings = listOf("Suicidality in children, adolescents and young adults during early treatment."),
            linkedSyndromes = listOf("mdd", "gad"),
            linkedCircuits = listOf("frontolimbic_circuit", "default_mode_network", "salience_network"),
            linkedTransmitters = listOf("serotonin", "norepinephrine"),
            clinicalPearls = "Balanced SNRI and first-line option when depression coexists with chronic pain, diabetic neuropathy or fibromyalgia; nausea and discontinuation symptoms demand slow titration.",
            summary = "Duloxetine is a balanced serotonin-norepinephrine reuptake inhibitor used in major depression, generalised anxiety and chronic pain syndromes including neuropathic pain and fibromyalgia. Its dual mechanism helps pain-depression comorbidity that pure SSRIs address poorly. Short half-life and dual withdrawal make slow tapering mandatory.",
            sources = listOf(Source("CANMAT 2023 MDD guideline (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC11351064/"), Source("DrugBank", "https://go.drugbank.com/"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"))
        ),
        Drug(
            id = "trazodone",
            genericName = "Trazodone",
            brandName = "Desyrel",
            drugClass = "Serotonin Antagonist and Reuptake Inhibitor (SARI)",
            atcCode = "N06AX05",
            receptorTargets = listOf(
                ReceptorTarget("5-HT2A", "Antagonist", null, "High", "Core antidepressant and sleep-promoting action"),
                ReceptorTarget("SERT", "Inhibitor", null, "Moderate", "Weak reuptake blockade, relevant at higher divided doses"),
                ReceptorTarget("H1 Histamine", "Antagonist", null, "Moderate", "Sedation exploited in low bedtime doses"),
                ReceptorTarget("Alpha-1 Adrenergic", "Antagonist", null, "Moderate", "Orthostatic hypotension and priapism risk")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "About 7 hours (immediate-release)",
                bioavailability = "Well absorbed orally",
                cypMetabolism = "Major CYP3A4 substrate; levels rise sharply with potent 3A4 inhibitors",
                timeToPeak = "1 to 2 hours fasting"
            ),
            dosingRange = DosingRange(
                startingDose = "50-100 mg at bedtime",
                targetDose = "150-300 mg/day in divided doses (depression)",
                maxDose = "600 mg/day (inpatient depression)",
                titrationSchedule = "Start low at bedtime for sleep; titrate toward divided antidepressant doses as tolerated."
            ),
            commonSideEffects = listOf("Sedation and morning grogginess", "Orthostatic hypotension", "Dry mouth", "Priapism (rare, emergency)", "QT prolongation"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Sedation", "Combined H1 and 5-HT2A blockade", "Give the bulk at bedtime; warn about driving"),
                SideEffectMechanism("Orthostatic hypotension", "Alpha-1 adrenergic blockade", "Slow posture changes; monitor elderly"),
                SideEffectMechanism("Priapism", "Alpha-adrenergic blockade", "Counsel all male patients; prolonged erection needs emergency care")
            ),
            blackBoxWarnings = listOf("Suicidality in children, adolescents and young adults during early treatment."),
            linkedSyndromes = listOf("mdd", "insomnia"),
            linkedCircuits = listOf("default_mode_network", "frontolimbic_circuit"),
            linkedTransmitters = listOf("serotonin"),
            clinicalPearls = "Low bedtime doses exploit H1 and 5-HT2A blockade for insomnia without dependence; full antidepressant doses require divided regimens and priapism counselling.",
            summary = "Trazodone is a multifunctional serotonergic agent used at low bedtime doses for insomnia and at higher divided doses for depression. Its receptor profile trades anticholinergic burden for sedation and orthostatic hypotension, with priapism as the signature rare emergency. CYP3A4 inhibitors substantially raise its levels.",
            sources = listOf(Source("CANMAT 2023 MDD guideline (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC11351064/"), Source("DrugBank", "https://go.drugbank.com/"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"))
        ),
        Drug(
            id = "haloperidol",
            genericName = "Haloperidol",
            brandName = "Haldol",
            drugClass = "First-Generation Antipsychotic (Butyrophenone)",
            atcCode = "N05AD01",
            receptorTargets = listOf(
                ReceptorTarget("D2", "Antagonist", null, "Very High", "Tight slow-dissociating blockade; antipsychotic effect with high EPS liability"),
                ReceptorTarget("Alpha-1 Adrenergic", "Antagonist", null, "Low", "Modest hypotension risk, far less than low-potency phenothiazines")
            ),
            pharmacokinetics = Pharmacokinetics(
                halfLife = "12-38 hours orally (decanoate depot lasts weeks)",
                bioavailability = "About 60% orally; decanoate depot every 4 weeks",
                cypMetabolism = "CYP2D6 and CYP3A4 substrate",
                timeToPeak = "2 to 6 hours orally"
            ),
            dosingRange = DosingRange(
                startingDose = "2-5 mg/day (0.5-2 mg in older adults)",
                targetDose = "5-10 mg/day",
                maxDose = "20 mg/day (higher only in refractory inpatient settings)",
                titrationSchedule = "Start low; titrate to the lowest effective dose; switch to decanoate every 4 weeks for maintenance."
            ),
            commonSideEffects = listOf("Extrapyramidal symptoms (dystonia, parkinsonism, akathisia)", "Tardive dyskinesia with chronic use", "QT prolongation", "Hyperprolactinemia", "Neuroleptic malignant syndrome (rare)"),
            sideEffectMechanisms = listOf(
                SideEffectMechanism("Acute dystonia and parkinsonism", "Striatal D2 occupancy above 80 percent", "Lower dose, add anticholinergic acutely, switch class if recurrent"),
                SideEffectMechanism("QT prolongation", "Cardiac potassium-channel blockade, worst with rapid IV use", "Baseline and serial ECGs; avoid IV boluses"),
                SideEffectMechanism("Neuroleptic malignant syndrome", "Central D2 blockade with rigidity, fever and autonomic instability", "Stop drug immediately; dantrolene and intensive support")
            ),
            blackBoxWarnings = listOf("Increased mortality in elderly patients with dementia-related psychosis."),
            linkedSyndromes = listOf("schizophrenia", "bipolar1"),
            linkedCircuits = listOf("mesolimbic_pathway", "mesocortical_pathway", "cstc_loop"),
            linkedTransmitters = listOf("dopamine"),
            clinicalPearls = "The benchmark high-potency first-generation antipsychotic: unmatched for acute agitation and delirium, but EPS and QT liability relegate it behind second-generation agents for maintenance.",
            summary = "Haloperidol is a high-potency dopamine-blocking antipsychotic that remains the reference for acute psychosis, mania and delirium, including intramuscular and long-acting decanoate forms. Its tight D2 binding brings dose-dependent extrapyramidal symptoms and tardive dyskinesia risk with chronic use. QT monitoring and the lowest effective dose govern safe prescribing.",
            sources = listOf(Source("IPS CPG 2025 update (PMC, free full text)", "https://pmc.ncbi.nlm.nih.gov/articles/PMC12900045"), Source("DrugBank", "https://go.drugbank.com/"), Source("NCBI Bookshelf / StatPearls", "https://www.ncbi.nlm.nih.gov/books/"))
        ),
    )

    fun searchAll(query: String, filterLayer: EntityLayer?): List<SearchResult> {
        val q = query.trim().lowercase()
        val results = mutableListOf<SearchResult>()

        if (filterLayer == null || filterLayer == EntityLayer.CIRCUITS) {
            circuits.filter {
                q.isEmpty() || it.name.lowercase().contains(q) ||
                        it.tierCode.lowercase().contains(q) ||
                        it.function.lowercase().contains(q) ||
                        it.brainStructures.any { s -> s.lowercase().contains(q) }
            }.forEach {
                results.add(
                    SearchResult(
                        id = it.id,
                        name = it.name,
                        subtitle = it.tierCode,
                        layer = EntityLayer.CIRCUITS,
                        codeBadge = it.tierCode,
                        summary = it.function
                    )
                )
            }
        }

        if (filterLayer == null || filterLayer == EntityLayer.TRANSMITTERS) {
            neurotransmitters.filter {
                q.isEmpty() || it.name.lowercase().contains(q) ||
                        it.symbol.lowercase().contains(q) ||
                        it.description.lowercase().contains(q) ||
                        it.receptorSubtypes.any { r -> r.name.lowercase().contains(q) || r.primaryFunction.lowercase().contains(q) }
            }.forEach {
                results.add(
                    SearchResult(
                        id = it.id,
                        name = it.name,
                        subtitle = "${it.symbol} • ${it.receptorSubtypes.size} Receptors",
                        layer = EntityLayer.TRANSMITTERS,
                        codeBadge = it.symbol,
                        summary = it.description.take(120) + "..."
                    )
                )
            }
        }

        if (filterLayer == null || filterLayer == EntityLayer.SYNDROMES) {
            syndromes.filter {
                q.isEmpty() || it.name.lowercase().contains(q) ||
                        it.icd11Code.lowercase().contains(q) ||
                        it.dsm5Code.lowercase().contains(q) ||
                        it.category.lowercase().contains(q) ||
                        it.clinicalDefinition.lowercase().contains(q)
            }.forEach {
                results.add(
                    SearchResult(
                        id = it.id,
                        name = it.name,
                        subtitle = "DSM-5: ${it.dsm5Code} • ICD-11: ${it.icd11Code}",
                        layer = EntityLayer.SYNDROMES,
                        codeBadge = it.icd11Code,
                        summary = it.clinicalDefinition.take(120) + "..."
                    )
                )
            }
        }

        if (filterLayer == null || filterLayer == EntityLayer.DRUGS) {
            drugs.filter {
                q.isEmpty() || it.genericName.lowercase().contains(q) ||
                        it.brandName.lowercase().contains(q) ||
                        it.drugClass.lowercase().contains(q) ||
                        it.atcCode.lowercase().contains(q) ||
                        it.receptorTargets.any { r -> r.target.lowercase().contains(q) }
            }.forEach {
                results.add(
                    SearchResult(
                        id = it.id,
                        name = it.genericName,
                        subtitle = "${it.brandName} • ${it.drugClass}",
                        layer = EntityLayer.DRUGS,
                        codeBadge = it.atcCode,
                        summary = "${it.drugClass}. Target dose: ${it.dosingRange.targetDose}."
                    )
                )
            }
        }

        return results
    }

    fun getCircuitById(id: String): Circuit? = circuits.find { it.id == id }
    fun getTransmitterById(id: String): Neurotransmitter? = neurotransmitters.find { it.id == id }
    fun getSyndromeById(id: String): Syndrome? = syndromes.find { it.id == id }
    fun getDrugById(id: String): Drug? = drugs.find { it.id == id }

    fun findEntityLayer(id: String): EntityLayer? {
        if (circuits.any { it.id == id }) return EntityLayer.CIRCUITS
        if (neurotransmitters.any { it.id == id }) return EntityLayer.TRANSMITTERS
        if (syndromes.any { it.id == id }) return EntityLayer.SYNDROMES
        if (drugs.any { it.id == id }) return EntityLayer.DRUGS
        return null
    }

    fun getEntityTitle(id: String): String {
        return getCircuitById(id)?.name
            ?: getTransmitterById(id)?.name
            ?: getSyndromeById(id)?.name
            ?: getDrugById(id)?.genericName
            ?: id
    }

    fun getEntitySubtitle(id: String): String {
        return getCircuitById(id)?.tierCode
            ?: getTransmitterById(id)?.symbol
            ?: getSyndromeById(id)?.dsm5Code?.let { "DSM-5: $it" }
            ?: getDrugById(id)?.brandName
            ?: ""
    }
}
