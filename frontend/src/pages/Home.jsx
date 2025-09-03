import { Link } from "react-router-dom";

const Home = () => {
    return ( 
        <div className="flex flex-col justify-center items-center h-screen bg-gray-100">
            <h1 className="text-4xl font-bold mb-4">Welcome to School Management</h1>
            <p className="text-center mb-8 text-gray-700">
                Please, log in or sign up to continue.
            </p>
            <div className="flex gap-4">
                
                <Link to="/login" className="ps-6 py-3 bg-blue-500 text-white rounded hover:bg-blue-600 transition"> Login</Link>
                <Link to="/signup" className="px-6 py-3 bg-green-500 text-white rounded hover:bg-green-600 transition"> Sign Up</Link>  
            </div>
        </div>
     );
}
 
export default Home;