using System.Collections.Generic;

namespace UGMan6000.Model
{
	/// <summary> 
	/// A Group has a name and a list of users that belong to it.
	/// </summary>
	public class Group
	{
		private string _name;
		private List<User> _users = new List<User>();

		public Group(string name)
		{
			_name = name;
		}

		public string Name
		{
			get { return _name;  }
		}

		public IEnumerable<User> Users
		{
			get { return _users; }
		}

		public void AddUser(User user)
		{
			if (_users.Contains(user)) return;
			
			user.AddedToGroup(this);
			_users.Add(user);
		}

		public override string ToString()
		{
			return _name;
		}
	}

	/// <summary>
	/// An User has a name and a list of group it belongs to.
	/// </summary>
	public class User
	{
		private string _name;
		private List<Group> _groups = new List<Group>();

		public User(string name)
		{
			_name = name;
		}

		public string Name
		{
			get { return _name; }
		}

		public IEnumerable<Group> Groups
		{
			get { return _groups; }
		}

		public override string ToString()
		{
			return _name;
		}
		
		/// <summary>
		/// Notifies the user it was added to a group.
		/// </summary>
		internal void AddedToGroup(Group g)
		{
			_groups.Add(g);
		}
	}
}
