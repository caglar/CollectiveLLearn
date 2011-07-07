from __future__ import division
import random
import sys

def weighted_choice_b(weights):
    totals = []
    running_total = 0

    for w in weights:
        running_total += w
        totals.append(running_total)

    rnd = random.random() * running_total
    return bisect.bisect_right(totals, rnd)

class BeliefUpdater:

    UpdateRate = 0.01

    @staticmethod
    def _updateSuccessTeacher (belief1, belief2):
        return belief1 + 5 * BeliefUpdater.UpdateRate * belief2

    @staticmethod
    def _updateFailTeacher (belief1, belief2):
        return belief1 - BeliefUpdater.UpdateRate * belief2

    @staticmethod
    def _updateSuccessLearner (belief1, belief2):
        return belief1 +  5 * BeliefUpdater.UpdateRate * belief2

    @staticmethod
    def _updateFailLearner (belief1, belief2):
        return belief1 * (1 - BeliefUpdater.UpdateRate) - BeliefUpdater.UpdateRate * (belief2 - belief1)

    @staticmethod
    def _updateFail (agentT, agentL):
        agentT.setBelief( BeliefUpdater._updateFailTeacher(agentT.getBelief(),
            agentL.getBelief()))
        agentL.setBelief( BeliefUpdater._updateFailLearner(agentL.getBelief(),
            agentT.getBelief()))

    @staticmethod
    def _updateSuccess(agentT, agentL):
        agentT.setBelief(
                BeliefUpdater._updateSuccessTeacher(agentT.getBelief(),
            agentL.getBelief()))
        agentL.setBelief(
                BeliefUpdater._updateSuccessLearner(agentL.getBelief(),
            agentT.getBelief()))

    @staticmethod
    def updateBelief (agent1, agent2, is_success):
        if (agent1.getRole() == 1):
            if (is_success):
                BeliefUpdater._updateSuccess(agent1, agent2)
            else:
                BeliefUpdater._updateFail(agent1, agent2)
        elif (agent1.getRole() == 0):
            if (is_success):
                BeliefUpdater._updateSuccess (agent2, agent1)
            else:
                BeliefUpdater._updateFail (agent2, agent2)

class Agent:

    _Role = 0 # 1 if teacher
    _CurrentWord = ""
    _Belief = 0.0

    def setRole (self, role):
        self._Role = role

    def getRole (self):
        return self._Role

    def setBelief (self, belief):
        self._Belief = belief

    def getBelief (self):
        return self._Belief

    def setCurrentWord (self, word):
        self._CurrentWord = word

    def getCurrentWord (self):
        return self._CurrentWord

    def hear (self, word):
        self.setCurrentWord(word)

    def speak (self):
        return self._CurrentWord

class Sim:

    _Lexicon = []
    _Agents = []
    _Map = {}
    _NoOfAgents = 10
    _NoOfSuccesses = 0
    _NoOfFailures = 0
    _NoOfIterations = 0
    _MaxDict = 0

    def setMaxDictSize(self, maxDict):
        self._MaxDict = maxDict

    def setLexicon (self, lex):
        self._Lexicon = lex

    def chooseRandomWord (self):
        l = len(self._Lexicon)
        if (self._MaxDict != 0):
            l = self._MaxDict
        n = random.randint(0, l-1)
        return self._Lexicon[n]

    def setNoOfAgents (self, noOfAgents):
        self._NoOfAgents = noOfAgents

    def getNoOfAgents (self):
        return self.NoOfAgents

    def getRandomAgent (self):
        l = len(self._Agents)
        rnd_val = random.randint(0, l-1)
        return self._Agents[rnd_val]

    def initAgents (self, noOfAgents=0):
        if (noOfAgents):
           self._NoOfAgents = noOfObjects
        for i in range (self._NoOfAgents):
            ag = Agent()
            rnd_word = self.chooseRandomWord()
            belief = random.uniform(0.05, 1)
            print rnd_word + str (belief)
            ag.setCurrentWord (rnd_word)
            ag.setBelief (belief)
            self._Agents.append (ag)

    def _initMap (self):
        for i in range (self._NoOfAgents):
            word = self._Agents[i].getCurrentWord()
            if self._Map.has_key(word):
                self._Map[word] += 1
            else:
                self._Map[word] = 1

    def getNoOfSuccesses (self):
        return self._NoOfSuccesses

    def getNoOfFailures (self):
        return self._NoOfFailures

    def getNoOfIterations (self):
        return self._NoOfIterations

    def playGames (self):
           l = len (self._Agents)
           self._initMap()
           is_success = 0
           while (len(self._Map) > 1):
            agent1 = self.getRandomAgent()
            agent2 = self.getRandomAgent()
            self._NoOfIterations += 1
            noOfCategories = len(self._Map)
            sys.stdout.write(str(noOfCategories) + " ")

            while (agent2 == agent1):
                agent2 = self.getRandomAgent()

            if (agent1.getBelief() > agent2.getBelief()):
                agent1.setRole(1)
                agent2.setRole(0)
            elif (agent2.getBelief() > agent1.getBelief()):
                agent1.setRole(0)
                agent2.setRole(1)
            else:
                agent1.setRole(1)
                agent2.setRole(1)

            if agent1.speak() == agent2.speak():
                self._NoOfSuccesses += 1
                is_success = 1
            else:
                is_success = 0
                self._NoOfFailures += 1
                if agent1.getRole() == 1:
                    lWord = agent2.speak()
                    sWord = agent1.speak()
                    self._Map[lWord] -= 1
                    self._Map[sWord] += 1
                    agent2.hear(sWord)
                    if self._Map[lWord] < 1:
                        del self._Map[lWord]
                else:
                    lWord = agent1.speak()
                    sWord = agent2.speak()
                    self._Map[lWord] -= 1
                    self._Map[sWord] += 1
                    agent1.hear(sWord)
                    if self._Map[lWord] == 0:
                        del self._Map[lWord]
                if len(self._Map) == 1:
                    break
            BeliefUpdater.updateBelief (agent1, agent2, is_success)
           for agent in self._Agents:
            print str(agent.getBelief()) + " " + agent.getCurrentWord()


def plot():
    pass

def main():
    cgcrbu = Sim()

    lex = ["Araba", "Bebek", "Kitap", "Baba", "Anne", "Kelebek", "Su", "Mama",
            "Yemek", "Adda", "Dede", "Puf", "Da", "Sabun", "Tren", "Nine",
            "Bebe", "Gece", "Sabah", "Masa", "Sehpa", "Sopa", "Kolpa", "Dolap",
            "Sokak", "Ev", "Deli", "Okul", "Koku", "Lavabo", "Kedi", "Veli",
            "Sokak", "Solak", "Manyak", "Salak", "Saat", "Vade", "Saka",
            "Sirk", "Firt", "Bot", "Kot", "Yat", "Bat", "Sat", "Kat", "Hat",
            "Bat", "Mat", "Pay", "Kar", "Hala", "Teyze", "Amca", "Proje",
            "Daire", "Yuvarlak", "Kare", "Sark", "Park", "Ben", "Erkek",
            "Adam", "Kiz", "Hiz", "Gaz", "Saz", "Kaz", "Hala", "Kola", "Balo",
            "Kart", "Sarp", "Harp", "Kan", "Kafa", "Bina", "Taban", "Saban",
            "Kaban", "Yaban", "Tavan", "Yavan", "Savan", "Hayvan", "Satan",
            "Sakal", "Bakkal", "Market", "Bilgi", "Ajan", "Bay", "Bayan",
            "Hatun", "Adam", "Saman", "Hakan", "Onur", "Bodur", "Korner",
            "Gol", "Manav", "Karpuz", "Kavun", "Karton", "Tok", "Yok", "Kil",
            "Sil", "Bil", "Pil", "Dil", "Dingil", "Silgi", "Kalem", "Motor",
            "Kart", "Nart", "Abart", "Sakat", "Tart", "Yat", "Mat", "Mart",
            "Dart", "Guatr", "Grip", "Nezle", "Sebze", "Meyve", "Mor", "Siyah",
            "Beyaz", "Lacivert", "Beyin", "Zihin", "Aroma", "Zamir", "Fiil",
            "Eylemsi", "Zarf", "Mektup", "Kasa"]

    cgcrbu.setLexicon(lex)
    cgcrbu.setNoOfAgents(10)
    cgcrbu.setMaxDictSize(10)
    cgcrbu.initAgents()
    cgcrbu.playGames()
    noOfIterations = cgcrbu.getNoOfIterations()
    noOfSuccess = cgcrbu.getNoOfSuccesses()
    noOfFailures = cgcrbu.getNoOfFailures()
    print "Iterations " + str(noOfIterations)
    print "No of Successes " + str(noOfSuccess)
    print "No of Failures " + str(noOfFailures)

main()
