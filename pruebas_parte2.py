import unittest
import simpy

class TestSimulacion(unittest.TestCase):
    def test_recursos(self):
        env = simpy.Environment()
        enfermeras = simpy.PriorityResource(env, capacity=3)
        doctores = simpy.PriorityResource(env, capacity=2)
        rayosX = simpy.PriorityResource(env, capacity=1)
        self.assertEqual(enfermeras.capacity, 3)
        self.assertEqual(doctores.capacity, 2)

if __name__ == '__main__':
    unittest.main()
